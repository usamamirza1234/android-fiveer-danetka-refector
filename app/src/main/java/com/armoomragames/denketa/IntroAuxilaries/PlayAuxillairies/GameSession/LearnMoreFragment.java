package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.R;

import java.util.ArrayList;

public class LearnMoreFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlBack, rlCross;
    int position = 0;
    Bundle bundle;   ArrayList<DModel_MyDenketa> lst_MyDenketa;
    boolean isInvestigator = false;
    boolean isMoreDanetka = false;
    TextView txvLearnmore;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_learn_more, container, false);

        init();
        bindViews(frg);
        setData();
        return frg;
    }

    private void setData() {
        txvLearnmore.setText(lst_MyDenketa.get(position).getLearnmore()+"");
        rlCross.setVisibility(View.GONE);

    }

    void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            isInvestigator = bundle.getBoolean("key_danetka_is_investigator", false);
            isMoreDanetka = bundle.getBoolean("key_danetka_is_more_danetka", false);
            lst_MyDenketa = bundle.getParcelableArrayList("list");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
        }
    }

    private void bindViews(View frg) {
        txvLearnmore = frg.findViewById(R.id.txvLearnmore);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
        }
    }
}
