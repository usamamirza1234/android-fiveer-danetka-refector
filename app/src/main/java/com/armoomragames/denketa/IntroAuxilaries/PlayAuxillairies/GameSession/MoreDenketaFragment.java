package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

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

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_More_Danektas;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomAlertDialog;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class MoreDenketaFragment extends Fragment implements IWebPaginationCallback, AbsListView.OnScrollListener {

    private static final String KEY_POSITION = "position";
    private static int Position;
    ListView lsvMoreDenekta;
    EditText edtSearch;
    ImageView imvSearch;
    MoreDenketaLsvAdapter adapter;
    Intro_WebHit_Get_More_Danektas intro_webHit_get_all__danektas;
    boolean isAlreadyFetching = false;
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    TextView txvUseGameCredits;
    private int nFirstVisibleItem, nVisibleItemCount, nTotalItemCount, nScrollState, nErrorMsgShown;
    private boolean isLoadingMore = false;
    private Dialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_more_denketa, container, false);
        init();
        bindViewss(frg);
        txvUseGameCredits.setText("Game Credits available " + AppConfig.getInstance().mUser.getGameCredits());
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

        intro_webHit_get_all__danektas = new Intro_WebHit_Get_More_Danektas();
        Intro_WebHit_Get_More_Danektas.mPaginationInfo.currIndex = AppConstt.PAGINATION_START_INDEX;
    }

    private void bindViewss(View frg) {
        lsvMoreDenekta = frg.findViewById(R.id.frg_rcv_my_denketa);
        edtSearch = frg.findViewById(R.id.frg_more_dankta_edt_search);
        imvSearch = frg.findViewById(R.id.frg_more_dankta_imv_search);

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

        Intro_WebHit_Get_More_Danektas.mPaginationInfo.currIndex = 1;
        Intro_WebHit_Get_More_Danektas.responseObject = null;
        intro_webHit_get_all__danektas.getCategory(this,
                Intro_WebHit_Get_More_Danektas.mPaginationInfo.currIndex);
    }

    //region Api and population more danetka
    private void populateAllDanektasData(boolean isSuccess, String strMsg) {

        dismissProgDialog();
        isAlreadyFetching = false;
        if (getActivity() != null && isAdded())
            if (isSuccess) {
                if (Intro_WebHit_Get_More_Danektas.responseObject != null &&
                        Intro_WebHit_Get_More_Danektas.responseObject.getData() != null &&
                        Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing() != null &&
                        Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().size() > 0) {
//                    txvNoData.setVisibility(View.GONE);
                    for (int i = 0; i < Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().size(); i++) {
                        lst_MyDenketa.add(
                                new DModel_MyDenketa(
                                        Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getTitle(),
                                        Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getId() + ""
                                        , Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getImage()
                                        , Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getQuestion()
                                        , Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getAnswer()
                                        , Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getAnswerImage()
                                        , Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getHint()
                                        , Intro_WebHit_Get_More_Danektas.responseObject.getData().getListing().get(i).getLearnMore(),false
                                )
                        );
                    }
                    if (adapter == null) {
                        Collections.sort(lst_MyDenketa, (o1, o2) -> o1.getStrName().compareTo(o2.getStrName()));
                        adapter = new MoreDenketaLsvAdapter(new IAdapterCallback() {
                            @Override
                            public void onAdapterEventFired(int eventId, int position) {
                                switch (eventId) {
                                    case EVENT_A:
                                        ((IntroActivity) getActivity()).navToDenketaInvestigatorQuestionFragment(position, false, true,lst_MyDenketa.get(position).getStrId(),lst_MyDenketa);
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
                    if (Intro_WebHit_Get_More_Danektas.mPaginationInfo.currIndex == 1) {
////                        lsvMedicines.setVisibility(View.GONE);
//                        txvNoData.setVisibility(View.VISIBLE);
////                        imvNoData.setVisibility(View.VISIBLE);
                        lsvMoreDenekta.setOnScrollListener(null);
                    }
                }
            } else {
                if (Intro_WebHit_Get_More_Danektas.mPaginationInfo.currIndex == 1) {
////                    lsvMedicines.setVisibility(View.GONE);
//                    txvNoData.setVisibility(View.VISIBLE);
////                    imvNoData.setVisibility(View.VISIBLE);
                    lsvMoreDenekta.setOnScrollListener(null);
                }
            }
    }

    private void updateDenketaList(boolean isSuccess, boolean isCompleted, String errorMsg) {
        isLoadingMore = false;
//        llListItemLoader.setVisibility(View.GONE);
//        if (progressDilogue != null) {
//            progressDilogue.stopiOSLoader();
//        }
        dismissProgDialog();

        if (getActivity() != null && isAdded())//check whether it is attached to an activity
            if (isSuccess) {
                if (isCompleted) {
                    Intro_WebHit_Get_More_Danektas.mPaginationInfo.isCompleted = true;
                } else {
                    populateAllDanektasData(isSuccess, errorMsg);
                }
            } else if (nErrorMsgShown++ < AppConstt.LIMIT_PAGINATION_ERROR) {
                CustomToast.showToastMessage(getActivity(), errorMsg, Toast.LENGTH_SHORT);
            }
    }

    @Override
    public void onWebInitialResult(boolean isSuccess, String strMsg) {
        populateAllDanektasData(isSuccess, strMsg);
    }

    @Override
    public void onWebSuccessiveResult(boolean isSuccess, boolean isCompleted, String strMsg) {
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
            if (!isLoadingMore && !Intro_WebHit_Get_More_Danektas.mPaginationInfo.isCompleted) {
                isLoadingMore = true;
//                llListItemLoader.setVisibility(View.VISIBLE);
                showProgDialog();
                intro_webHit_get_all__danektas.getCategory(this,
                        Intro_WebHit_Get_More_Danektas.mPaginationInfo.currIndex + 1);
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

    // Store instance variables based on arguments passed
    public static Fragment newInstance(int position) {
        Fragment frag = new MoreDenketaFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        Position = position;
        frag.setArguments(args);
        return (frag);
    }

}
