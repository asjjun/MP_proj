package com.SW_FINAL.mp_proj;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketFragment extends Fragment implements PurchasesUpdatedListener, PurchaseHistoryResponseListener {
    public View view;
    private Button  diamond_10, diamond_50, diamond_100;
    private BillingClient billingClient;
    public ArrayList<String> purchasableList = new ArrayList<>();
    DatabaseReference ref;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_market, container, false);

        diamond_10 = (Button)view.findViewById(R.id.diamond_10);
        diamond_50 = (Button)view.findViewById(R.id.diamond_50);
        diamond_100 = (Button)view.findViewById(R.id.diamond_100);

        context = container.getContext();
        initBillingClient();
        return view;
    }
    private void initBillingClient(){
        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                    getAcknowledgePurchasedItem();
                    getAllPurchasedItem();
                    setPurchasableList();
                    setUpViews();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.e("TAG", "Service Disconnected.");
            }
        });
    }

    public void getAcknowledgePurchasedItem(){
        // BillingClient 의 준비가 되지않은 상태라면 돌려보낸다
        if (!billingClient.isReady()) {
            return;
        }

        // 인앱결제된 내역을 확인한다
        Purchase.PurchasesResult result = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        if (result.getPurchasesList() == null) {
            Log.d("TAG", "No existing in app purchases found.");
        }
        else {
            Log.d("TAG", "Existing Once Type Item Bought purchases: "+result.getPurchasesList());

        }
    }

    public void getAllPurchasedItem() {
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,this);
    }

    private void setPurchasableList(){
        // Google PlayConsole 의 상품Id 와 동일하게 적어준다
        purchasableList.add("diamond_10");
        purchasableList.add("diamond_50");
        purchasableList.add("diamond_100");

    }

    private void setUpViews() {
        diamond_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseItem( 0);
            }
        });
        diamond_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseItem( 2);
            }
        });
        diamond_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseItem( 1);
            }
        });
    }

    private void purchaseItem(final int listNumber)
    {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(purchasableList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult result,
                                                     List<SkuDetails> skuDetails) {
                        // Process the result.
                        if(result.getResponseCode() == BillingClient.BillingResponseCode.OK && !skuDetails.isEmpty()){
                            BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails.get(listNumber)).build();
                            billingClient.launchBillingFlow((Activity) context, flowParams); // MainActivity.this
                        }
                        else{
                            Log.e("TAG", "No sku found from query");
                        }
                    }
                });

    }


    @Override
    public void onPurchaseHistoryResponse(@NonNull BillingResult billingResult, @Nullable List<PurchaseHistoryRecord> list) {
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
            if(!list.isEmpty()){
                for(PurchaseHistoryRecord it :list){
                    Log.d("TAG", "Previous Purchase Item: " +it.getOriginalJson());
                }
            }
        }

    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list !=null ){
            for(Purchase purchase : list)
            {
                if(purchase.getSku().equals("diamond_10")){
                    purchaseAlways(purchase.getPurchaseToken(),1);
                }
                else if(purchase.getSku().equals("diamond_50"))
                {
                    purchaseAlways(purchase.getPurchaseToken(),2);
                }
                else
                {
                    purchaseAlways(purchase.getPurchaseToken(),3);
                }

            }
        }
        else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED){
            Log.d("TAG", "You've cancelled the Google play billing process...");
        }
        else{
            Log.e(
                    "TAG",
                    "Item not found or Google play billing error... : "+billingResult.getResponseCode()
            );
        }

    }

    private void purchaseAlways(String purchaseToken, int num){
        ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchaseToken).build();
        billingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    if(num == 1)
                    {
                        upDateDiamond(10);
                    }
                    else if(num == 2)
                    {
                        upDateDiamond(50);
                    }
                    else{
                        upDateDiamond(100);
                    }

                    Toast.makeText(context, "구매에 성공했습니다", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });


    }


    void upDateDiamond(int dia_num){
        ref = FirebaseDatabase.getInstance().getReference("users").child(Storage.MyId);
        Storage.Mydiamond += dia_num;
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("diamond", Storage.Mydiamond);
        ref.updateChildren(objectMap);
    }

}