package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Delete_AdminDanetkas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_User_Danektas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_B;
import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_C;

public class MyDenketaFragment extends Fragment implements View.OnClickListener, IWebPaginationCallback, AbsListView.OnScrollListener {

    private static final String KEY_POSITION = "position";
    MyDenketaLsvAdapter adapter;
    ListView rcvMyDenekta;
    Dialog dialog;
    Intro_WebHit_Get_User_Danektas intro_webHit_get_user_danektas;
    boolean isAlreadyFetching = false;
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    EditText edtSearch;
    ImageView imvSearch;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    private boolean isLoadingMore = false;
    private Dialog progressDialog;

    public static Fragment newInstance(int position) {
        Fragment frag = new MyDenketaFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_my_denketa, container, false);
        init();
        bindViewss(frg);
        requestDenketa();
        return frg;
    }

    private void init() {
        lst_MyDenketa = new ArrayList<>();
        nFirstVisibleItem = 0;
        nVisibleItemCount = 0;
        nTotalItemCount = 0;
        nScrollState = 0;
        nErrorMsgShown = 0;
        isLoadingMore = false;
        intro_webHit_get_user_danektas = new Intro_WebHit_Get_User_Danektas();
        Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
    }

    private void bindViewss(View frg) {
        rcvMyDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);
        edtSearch = frg.findViewById(R.id.frg_my_dankta_edt_search);
        imvSearch = frg.findViewById(R.id.frg_my_dankta_imv_search);

        imvSearch.setOnClickListener(this);
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

    //region Api and population more danetka
    public void requestDenketa() {
        isAlreadyFetching = true;
        showProgDialog();
        Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex = 1;
        Intro_WebHit_Get_User_Danektas.responseObject = null;
        intro_webHit_get_user_danektas.getMyDanekta(this, Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex);
    }

    private void populateAllDanektasData(boolean isSuccess, String strMsg) {
        dismissProgDialog();
        isAlreadyFetching = false;
        if (isSuccess) {
            if (AppConfig.getInstance().mUser.isLoggedIn()) {
                if (Intro_WebHit_Get_User_Danektas.responseObject != null &&
                        Intro_WebHit_Get_User_Danektas.responseObject.getData() != null &&
                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing() != null &&
                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().size() > 0) {
                    for (int i = 0; i < Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().size(); i++) {
                        lst_MyDenketa.add(
                                new DModel_MyDenketa(
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getTitle(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getId() + "",
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getImage(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getQuestion(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getAnswer(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getAnswerImage(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getHint(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getLearnMore(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getIsPlayed(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getDanetkas().getDanetkaType(),
                                        Intro_WebHit_Get_User_Danektas.responseObject.getData().getListing().get(i).getId() + ""));
                    }
                    if (adapter == null) {
                        adapter = new MyDenketaLsvAdapter((eventId, position) -> {
                            switch (eventId) {
                                case IAdapterCallback.EVENT_A:
                                    if (!AppConfig.getInstance().getProgDialogs())
                                        onClickDenketaItem(position);
                                    else {
                                        if (lst_MyDenketa.get(position).isPlayed()) {
                                            ((IntroActivity) getActivity()).navToDenketaAnswerFragment(position, false, false, lst_MyDenketa.get(position).getStrId(), lst_MyDenketa);

                                        } else
                                            ((IntroActivity) getActivity()).navToDenketaInvestigatorQuestionFragment(position, false, false, lst_MyDenketa.get(position).getStrId(), lst_MyDenketa);
                                    }
                                    break;
                                case EVENT_B:
                                    Log.d("Danetka", "ID " + lst_MyDenketa.get(position).getStrId());
                                    ((IntroActivity) getActivity()).navToMyResultsFragment(lst_MyDenketa.get(position).getStrName(), lst_MyDenketa.get(position).getStrId());
                                    break;
                                case EVENT_C:
                                    Log.d("Danetka", "ID " + lst_MyDenketa.get(position).getStrId());
                                    openDialogCredits(Integer.parseInt(lst_MyDenketa.get(position).getStrId()), position);
                                    break;
                            }
                        }, getActivity(), lst_MyDenketa);
                        rcvMyDenekta.setAdapter(adapter);
                        rcvMyDenekta.setOnScrollListener(this);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        rcvMyDenekta.setOnScrollListener(null);
                    }
                }
            } else {
                if (Intro_WebHit_Get_User_Danektas.responseObjectGuest != null &&
                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData() != null &&
                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing() != null &&
                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().size() > 0) {
                    for (int i = 0; i < Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().size(); i++) {
                        lst_MyDenketa.add(
                                new DModel_MyDenketa(
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getTitle(),
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getId() + "",
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getImage(),
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getQuestion(),
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getAnswer(),
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getAnswerImage(),
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getHint(),
                                        Intro_WebHit_Get_User_Danektas.responseObjectGuest.getData().getListing().get(i).getLearnMore(),
                                        false
                                ));
                    }
                    if (adapter == null) {
                        adapter = new MyDenketaLsvAdapter((eventId, position) -> {
                            switch (eventId) {
                                case IAdapterCallback.EVENT_A:
                                    if (!AppConfig.getInstance().getProgDialogs())
                                        onClickDenketaItem(position);
                                    else {
                                        if (lst_MyDenketa.get(position).isPlayed()) {
                                            ((IntroActivity) getActivity()).navToDenketaAnswerFragment(position, false, false, lst_MyDenketa.get(position).getStrId(), lst_MyDenketa);

                                        } else
                                            ((IntroActivity) getActivity()).navToDenketaInvestigatorQuestionFragment(position, false, false, lst_MyDenketa.get(position).getStrId(), lst_MyDenketa);
                                    }
                                    break;
                                case EVENT_B:
                                    Log.d("Danetka", "ID " + lst_MyDenketa.get(position).getStrId());
                                    ((IntroActivity) getActivity()).navToMyResultsFragment(lst_MyDenketa.get(position).getStrName(), lst_MyDenketa.get(position).getStrId());
                                    break;
                                case EVENT_C:
                                    Log.d("Danetka", "ID " + lst_MyDenketa.get(position).getStrId());
                                    openDialogCredits(Integer.parseInt(lst_MyDenketa.get(position).getStrId()), position);
                                    break;
                            }
                        }, getActivity(), lst_MyDenketa);
                        rcvMyDenekta.setAdapter(adapter);
                        rcvMyDenekta.setOnScrollListener(this);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        rcvMyDenekta.setOnScrollListener(null);
                    }
                }
            }
        } else {
            if (Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                rcvMyDenekta.setOnScrollListener(null);
            }
        }
    }

    public void openDialogCredits(int _id, int position) {


        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popup_dark)));
        progressDialog.setContentView(R.layout.dialog_unclock_danetka);
        TextView txvDetails = progressDialog.findViewById(R.id.dailog_txvDetails);
        TextView txv_dialoge_yes = progressDialog.findViewById(R.id.txv_dialoge_yes);
        TextView txv_dialoge_no = progressDialog.findViewById(R.id.txv_dialoge_no);
        txv_dialoge_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestDeleteDanetka(_id, position);
            }
        });
        txv_dialoge_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
            }
        });
        int GC = Integer.parseInt(AppConfig.getInstance().mUser.getGameCredits());

//        if (GC != 0)
        txvDetails.setText("Are you sure you want\n to delete this Danetka?");
//        else {
//            txvDetails.setText("You don’t have enough\n  Game Credits.");
//            txv_dialoge_yes.setVisibility(View.GONE);
//            txv_dialoge_no.setVisibility(View.GONE);
//        }

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
                    lst_MyDenketa.remove(position);
                    adapter.notifyDataSetChanged();
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

    private void updateDenketaList(boolean isSuccess, boolean isCompleted, String
            errorMsg) {
        isLoadingMore = false;
        dismissProgDialog();
        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_User_Danektas.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
                CustomToast.showToastMessage(getActivity(), errorMsg, Toast.LENGTH_SHORT);
            }
    }

    public void onClickDenketaItem(int position) {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_master_rules);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CheckBox checkBox;
        checkBox = dialog.findViewById(R.id.lay_prog_chb);
        TextView txvRules = dialog.findViewById(R.id.lay_item_play_txvRules);

        txvRules.setOnClickListener(this);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.getInstance().setProgDialogs(true);
            }
        });

        LinearLayout llOkay = dialog.findViewById(R.id.lay_item_rules_llOkay);
        llOkay.setOnClickListener(v -> {
            dialog.dismiss();
            if (lst_MyDenketa.get(position).isPlayed()) {
                ((IntroActivity) getActivity()).navToDenketaAnswerFragment(position, false, false, lst_MyDenketa.get(position).getStrId(), lst_MyDenketa);
            } else
                ((IntroActivity) getActivity()).navToDenketaInvestigatorQuestionFragment(position, false, false, lst_MyDenketa.get(position).getStrId(), lst_MyDenketa);
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_item_play_txvRules:
                dialog.dismiss();
                ((IntroActivity) getActivity()).navToRulesFragment();
                break;
            case R.id.frg_my_dankta_imv_search:
//                if (edtSearch.getText().toString().equalsIgnoreCase(" "))
//                    edtSearch.setText("");
//                if (!edtSearch.getText().toString().equalsIgnoreCase(""))
//                    filter(edtSearch.getText().toString());

//                if (edtSearch.getText().toString().equalsIgnoreCase(""))
//                    populateAllDanektasData(true, "");
                break;

        }
    }

    @Override
    public void onWebInitialResult(boolean isSuccess, String strMsg) {
        populateAllDanektasData(isSuccess, strMsg);
    }

    @Override
    public void onWebSuccessiveResult(boolean isSuccess,
                                      boolean isCompleted, String strMsg) {
        updateDenketaList(isSuccess, isCompleted, strMsg);
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
            if (AppConfig.getInstance().mUser.isLoggedIn()) {
                if (!isLoadingMore && !Intro_WebHit_Get_User_Danektas.mPaginationInfo.isCompleted) {
                    isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);

                    intro_webHit_get_user_danektas.getMyDanekta(this,
                            Intro_WebHit_Get_User_Danektas.mPaginationInfo.currIndex + 1);
                }

            }
        }
    }

    private void filter(String text) {
    }
    //endregion


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

}

