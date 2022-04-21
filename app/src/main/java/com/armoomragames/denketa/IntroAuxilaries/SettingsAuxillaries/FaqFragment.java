package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.ContactFragment;
import com.armoomragames.denketa.IntroAuxilaries.DModelDictionary;
import com.armoomragames.denketa.IntroAuxilaries.DictionaryRCVAdapter;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class FaqFragment extends Fragment implements View.OnClickListener {

    RelativeLayout rlToolbar, rlBack, rlCross;
    RecyclerView lsvFaq;
    ArrayList<DModelDictionary> lst_Funds;

    TextView txvContactus;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_faq, container, false);


        init();
        bindViews(frg);
        populateData();
        return frg;
    }

    IBadgeUpdateListener mBadgeUpdateListener;

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);

        }

    }

    void init() {
        setToolbar();
        lst_Funds = new ArrayList<>();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }


    private void bindViews(View frg) {

        lsvFaq = frg.findViewById(R.id.frg_lsv_faq);
        Animation shake;
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);

        TextView txvPlay = frg.findViewById(R.id.frg_presigin_txvPlay);
        txvContactus = frg.findViewById(R.id.txvContactus);
        txvContactus.setOnClickListener(this);
//        txvPlay.startAnimation(shake); // starts animation
    }


    private void populateData() {

        FaqListAdapter faqListAdapter = null;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (faqListAdapter == null) {
            lst_Funds.add(new DModelDictionary("Do I need to know music theory or be a musician to play? \t\t", "No! In this game no musical knowledge is required. Only critical thinking. "));
            lst_Funds.add(new DModelDictionary("Do I have to pay to play Danetkas? ", "You get 3 Danetkas for free using this app! To unlock new Danetkas you have to pay. "));
            lst_Funds.add(new DModelDictionary("Can I play the same Danetka multiple times? ", "As an investigator you can only play it one time. Once you know the answer, you can play as a Master as many times as you want with different people. "));
            lst_Funds.add(new DModelDictionary("What am I supposed to find out? I don’t know what to ask! ", "Examine the hint carefully. Ask yourself if everything is clear for you and you can explain the story. If you cannot explain any part of it, ask “Yes/No” questions to find out more details. Maybe a Rigoletto can help you think outside of the box… "));
            lst_Funds.add(new DModelDictionary("I´m the Master. Can I use intonations and gestures? ", "Yes, you can. The game can be harder or easier, depending how much help the Master gives to the Investigator/s."));
            lst_Funds.add(new DModelDictionary("What is the maximum number of people that I can play Danetkas with? ", "You are free to have as many Investigators making questions as you want. You can make it into a party game! Two Masters at the same time are not allowed."));
            lst_Funds.add(new DModelDictionary("Can I play this with my kids? ", "Yes, you can! But as a Master you might need to give extra help that is not covered on our official “Rules” Your kids can also play the role of the “Master” and that works very well. "));
            lst_Funds.add(new DModelDictionary("Can I use your game for educational purposes? ", "Please, do! "));
            lst_Funds.add(new DModelDictionary("Do you have a physical version of the game? ", "We are working on it! "));

            faqListAdapter = new FaqListAdapter(getActivity(), lst_Funds, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            lsvFaq.setLayoutManager(linearLayoutManager);
            lsvFaq.setAdapter(faqListAdapter);

        } else {
            faqListAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txvContactus:


                navtoContactFragment();

                break;
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity) getActivity()).onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;
            case R.id.frg_settings_rlMyAccount:


                break;
        }
    }

    private void navtoContactFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment frag = new ContactFragment();
        //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
        //  R.anim.enter_from_left, R.anim.exit_to_right);//not required
        ft.add(R.id.act_intro_content_frg, frag, AppConstt.FragTag.FN_ContactFragment);
        ft.addToBackStack(AppConstt.FragTag.FN_ContactFragment);
        ft.hide(this);
        ft.commit();
    }
}
