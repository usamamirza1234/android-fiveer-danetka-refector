package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results.RateAppFragment;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.JustifyTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class AnswerFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlBack, rlCross;
    RecyclerView rcvRegilto;
    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    TextView txvDanetkaName;
    TextView txvDetail;
    IBadgeUpdateListener mBadgeUpdateListener;
    ImageView img;
    LinearLayout llLearnMore;
    LinearLayout llEndGame;
    AnwserRegiltoRCVAdapter anwserRegiltoRCVAdapter;
    String danetka_Image;
    int position = 0;
    Bundle bundle;
    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    String[] lstRegilto;
    String danetka_ID = "1";
    String formattedDate = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_denketa_answer, container, false);

        init();
        bindViews(frg);
        setData();

        return frg;
    }

    private void setData() {
        try {
            danetka_ID = (lst_MyDenketa.get(position).getStrId()) + "";
            txvDanetkaName.setText(lst_MyDenketa.get(position).getStrName());
            txvDetail.setText(lst_MyDenketa.get(position).getAnswer() + "");
            danetka_Image = "http://18.119.55.236:2000/images/" + lst_MyDenketa.get(position).getAnswerImage();
//                        Glide.with(getContext()).load(danetka_Image).into(img);
            lstRegilto = (lst_MyDenketa.get(position).getHint().split("\\s*=\\s*"));
            populatePopulationList();

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

            Glide.with(getContext())
                    .load(danetka_Image)
                    .apply(options)
                    .into(img);


            if (lst_MyDenketa.get(position).getLearnmore().equals("l"))
            {
                llLearnMore.setVisibility(View.GONE);
            }

        } catch (Exception e) {

        }

    }

    //region init
    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

        }

    }

    void init() {
        setToolbar();

        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);
            lst_MyDenketa = bundle.getParcelableArrayList("list");
        }

        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            formattedDate = df.format(c);
        } catch (Exception e) {
        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    private void bindViews(View frg) {
        txvDanetkaName = frg.findViewById(R.id.frg_my_results_txv_danetkaname);
        txvDetail = frg.findViewById(R.id.detail);
        rcvRegilto = frg.findViewById(R.id.frg_make_rcv_regilto);


        img = frg.findViewById(R.id.img);
        llLearnMore = frg.findViewById(R.id.frg_denketa_answer_llLearnmore);
        llEndGame = frg.findViewById(R.id.frg_denketa_answer_llEndGame);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        llEndGame.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
        llLearnMore.setOnClickListener(this);
    }

    //endregion
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.frg_denketa_answer_llEndGame:


                if (!isInvestigator) {
                    if (AppConfig.getInstance().mUser.isLoggedIn())
                        navToRateAppFragment();
                    else
                        ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                } else ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;

            case R.id.frg_denketa_answer_llLearnmore:
                navToLearnmoreFragment();
                break;
        }
    }


    public void navToLearnmoreFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new LearnMoreFragment();
        Bundle bundle = new Bundle();        bundle.putParcelableArrayList("list", lst_MyDenketa);
        bundle.putInt("key_danetka_position", position);
        bundle.putBoolean("key_danetka_is_investigator", isInvestigator);
        bundle.putBoolean("key_danetka_is_more_danetka", isMoreDanetka);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_LearnMoreFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_LearnMoreFragment);
        ft.hide(this);
        ft.commit();

    }


    public void navToRateAppFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new RateAppFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_danetka_id", danetka_ID);
        bundle.putString("key_danetka_name", txvDanetkaName.getText().toString());
        bundle.putString("key_danetka_formattedDate", formattedDate);
        frag.setArguments(bundle);
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_RateAppFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_RateAppFragment);
        ft.hide(this);
        ft.commit();

    }

    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (anwserRegiltoRCVAdapter == null) {

            anwserRegiltoRCVAdapter = new AnwserRegiltoRCVAdapter(getActivity(), lstRegilto, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            rcvRegilto.setLayoutManager(linearLayoutManager);
            rcvRegilto.setAdapter(anwserRegiltoRCVAdapter);

        } else {
            anwserRegiltoRCVAdapter.notifyDataSetChanged();
        }
    }
}
