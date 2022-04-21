package com.armoomragames.denketa.IntroAuxilaries.Admin.DanetkaDetails;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Delete_AdminDanetkas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_All_Danektas_Admin;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomAlertDialog;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class DanetkaDetailsFragment extends Fragment implements View.OnClickListener, IWebPaginationCallback, AbsListView.OnScrollListener {

    private static final String KEY_POSITION = "position";
    RelativeLayout rlToolbar, rlBack, rlCross;
    ListView lsvMoreDenekta;
    String strID = "0";
    EditText edtSearch;
    ImageView imvSearch;
    CustomAlertDialog customAlertDialog;
    DenketaDetailsLsvAdapter adapter;
    Intro_WebHit_Get_All_Danektas_Admin intro_webHit_get_all__danektas;
    boolean isAlreadyFetching = false;

    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    ArrayList<DModel_MyDenketa> lst_MyDenketaFiltered;
    TextView txvUseGameCredits;
    FloatingActionButton fab;
    private Dialog progressDialog;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    private boolean isLoadingMore = false;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_details, container, false);
        init();
        bindViewss(frg);
        requestDenketa();
        return frg;
    }

    private void init() {
        lst_MyDenketa = new ArrayList<>();
        lst_MyDenketaFiltered = new ArrayList<>();

        nFirstVisibleItem = 0;
        nVisibleItemCount = 0;
        nTotalItemCount = 0;
        nScrollState = 0;
        nErrorMsgShown = 0;
        isLoadingMore = false;

        intro_webHit_get_all__danektas = new Intro_WebHit_Get_All_Danektas_Admin();
        Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
    }

    private void bindViewss(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        lsvMoreDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);
        edtSearch = frg.findViewById(R.id.frg_more_dankta_edt_search);
        imvSearch = frg.findViewById(R.id.frg_more_dankta_imv_search);
        fab = frg.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        txvUseGameCredits = frg.findViewById(R.id.txvUseGameCredits);

        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(edtSearch.getText().toString());
            }
        });
    }


    public void requestDenketa() {
        isAlreadyFetching = true;
        showProgDialog();

        Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.currIndex = 1;
        Intro_WebHit_Get_All_Danektas_Admin.responseObject = null;
        intro_webHit_get_all__danektas.getCategory(this,
                Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.currIndex);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.fab:

                navtoAdddanetkaFragment();
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
//            init();
//            requestDenketa();
        }
    }

    //region Api and population more danetka
    private void populateAllDanektasData(boolean isSuccess, String strMsg) {

        dismissProgDialog();

        isAlreadyFetching = false;
        if (getActivity() != null && isAdded())
            if (isSuccess) {

                if (Intro_WebHit_Get_All_Danektas_Admin.responseObject != null &&
                        Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData() != null &&
                        Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing() != null &&
                        Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().size() > 0) {

//                    txvNoData.setVisibility(View.GONE);

                    for (int i = 0; i < Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().size(); i++) {
                        lst_MyDenketa.add(
                                new DModel_MyDenketa(
                                        Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getTitle(),
                                        Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getId() + ""
                                        , Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getImage()
                                        , Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getQuestion()
                                        , Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getAnswer()
                                        , Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getAnswerImage()
                                        , Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getHint()
                                        , Intro_WebHit_Get_All_Danektas_Admin.responseObject.getData().getListing().get(i).getLearnMore(),false
                                )
                        );
                    }


                    if (adapter == null) {
                        Collections.sort(lst_MyDenketa, (o1, o2) -> o1.getStrName().compareTo(o2.getStrName()));

                        if (lst_MyDenketaFiltered.size() <= 0) {
                            lst_MyDenketaFiltered = lst_MyDenketa;
                        }

                        adapter = new DenketaDetailsLsvAdapter(new IAdapterCallback() {
                            @Override
                            public void onAdapterEventFired(int eventId, int position) {
                                switch (eventId) {
                                    case EVENT_A:
                                        popupDialogue(position, lst_MyDenketaFiltered);
                                        break;
                                }

                            }
                        }, getActivity(), lst_MyDenketa);
                        lsvMoreDenekta.setAdapter(adapter);
                        lsvMoreDenekta.setOnScrollListener(this);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.currIndex == 1) {
                        lsvMoreDenekta.setOnScrollListener(null);
                    }
                }
            } else {
                if (Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.currIndex == 1) {
                    lsvMoreDenekta.setOnScrollListener(null);
                }
            }
    }


    private void updateDenketaList(boolean isSuccess, boolean isCompleted, String errorMsg) {
        isLoadingMore = false;
        dismissProgDialog();

        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
            }
    }

    @Override
    public void onWebInitialResult(boolean isSuccess, String strMsg) {
        populateAllDanektasData(isSuccess, strMsg);
    }

    @Override
    public void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg) {
//        updateDenketaList(isSuccess, isCompleted, strMsg);
    }

    @Override
    public void onWebInitialException(Exception ex) {
        populateAllDanektasData(false, AppConfig.getInstance().getNetworkExceptionMessage(ex.toString()));
    }

    @Override
    public void onWebSuccessiveException(Exception ex) {
        updateDenketaList(false, false, AppConfig.getInstance().getNetworkExceptionMessage(ex.getMessage()));
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.nScrollState = i;
        this.isScrollCompleted();
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.nFirstVisibleItem = i;
        this.nVisibleItemCount = i1;
        this.nTotalItemCount = i2;
    }

    private void isScrollCompleted() {
        if (this.nVisibleItemCount > 0 && this.nScrollState == SCROLL_STATE_IDLE &&
                this.nTotalItemCount == (nFirstVisibleItem + nVisibleItemCount)) {
            /*** In this way I detect if there's been a scroll which has completed ***/
            if (!isLoadingMore && !Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.isCompleted) {
                isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);

                intro_webHit_get_all__danektas.getCategory(this,
                        Intro_WebHit_Get_All_Danektas_Admin.mPaginationInfo.currIndex + 1);
            }
        }
    }
    //endregion


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DModel_MyDenketa> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (DModel_MyDenketa item : lst_MyDenketa) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getStrName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found for word" + text, Toast.LENGTH_SHORT).show();
//            adapter.filterList(lst_MyDenketa);
        } else {
            // at last we are passing that filtered
            // list to our adapter class.

            lst_MyDenketaFiltered = filteredlist;
            adapter.filterList(filteredlist);

//            Toast.makeText(getContext(), "Data Found.." + text, Toast.LENGTH_SHORT).show();
        }
    }


    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void navtoEditDenketaFragment(int position, ArrayList<DModel_MyDenketa> lst_myDenketaFiltered) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new EditDenketaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key_danetka_position", position);
        bundle.putParcelableArrayList("list", lst_myDenketaFiltered);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_EditDenketaFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_EditDenketaFragment);
        ft.hide(this);
        ft.commit();
    }


    private void navtoAdddanetkaFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new CreateAdminDenketaFragment();
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_CreateAdminDenketaFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_CreateAdminDenketaFragment);
        ft.hide(this);
        ft.commit();
    }

    private void popupDialogue(int position, ArrayList<DModel_MyDenketa> lst_myDenketaFiltered) {
        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_danetka_details);
        TextView txvDetails = progressDialog.findViewById(R.id.dailog_txvDetails);
        TextView txv_dialoge_yes = progressDialog.findViewById(R.id.txv_dialoge_yes);
        TextView txv_dialoge_no = progressDialog.findViewById(R.id.txv_dialoge_no);
        TextView txv_dialoge_cancel = progressDialog.findViewById(R.id.txv_dialoge_cancel);
        txvDetails.setText("Press EDIT to modify \n “" + lst_myDenketaFiltered.get(position).getStrName() + "” \n or press DELETE to delete \n this “DANETKAS”");


        txv_dialoge_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.dismiss();
                navtoEditDenketaFragment(position, lst_myDenketaFiltered);
            }
        });
        txv_dialoge_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.dismiss();
            }
        });

        txv_dialoge_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.dismiss();
                requestDeleteDanetka(Integer.parseInt(lst_myDenketaFiltered.get(position).getStrId()), position);
            }
        });

        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);

        progressDialog.show();
    }

    private void requestDeleteDanetka(int _id, int position) {


        Intro_WebHit_Delete_AdminDanetkas intro_webHit_delete_adminDanetkas = new Intro_WebHit_Delete_AdminDanetkas();
        intro_webHit_delete_adminDanetkas.deleteDanetka(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Deleted SUCCESSFULLY.", Toast.LENGTH_SHORT);
                    lst_MyDenketaFiltered.remove(position);
                    adapter.notifyDataSetChanged();
                    for (int i = 0; i < lst_MyDenketa.size(); i++) {
                        if (lst_MyDenketa.get(i).getStrId().equalsIgnoreCase(_id + "")) {
                            lst_MyDenketa.remove(i);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
            }
        }, _id);
    }
}
