package com.armoomragames.denketa.IntroAuxilaries;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class DictionaryFragment extends Fragment implements View.OnClickListener {


    IBadgeUpdateListener mBadgeUpdateListener;
    DictionaryRCVAdapter dictionaryRCVAdapter = null;
    RecyclerView lsvDictionary;
    ArrayList<DModelDictionary> lst_Funds;

    ImageView imvSearch;
    EditText edt_Search;
    RelativeLayout rlToolbar, rlBack, rlCross;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_dictionary, container, false);


        init();
        bindViews(frg);
//        populateData();
        populatePopulationList();
        return frg;
    }

    private void bindViews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        imvSearch = frg.findViewById(R.id.imv_search);
        lsvDictionary = frg.findViewById(R.id.frg_lsv_dictionary);
        edt_Search = frg.findViewById(R.id.edt_Search);

        imvSearch.setOnClickListener(this);


        edt_Search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(edt_Search.getText().toString());
            }
        });
    }




    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_BACK_HIDDEN);

        }

    }

    void init() {
        lst_Funds = new ArrayList<>();
        setToolbar();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity)getActivity()).  onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity)getActivity()). navToPreSignInVAFragment();

                break;
            case R.id.imv_search:
                filter(edt_Search.getText().toString());
                break;
        }
    }


    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (dictionaryRCVAdapter == null)
        {



            lst_Funds.add(new DModelDictionary(" Accompanist ", "a person who provides a musical accompaniment to another instrumentalist or to a singer."));
            lst_Funds.add(new DModelDictionary(" Arrangement ", "new version of a previously composed work, usually employing new instrumentation."));
            lst_Funds.add(new DModelDictionary(" Aria ", "a self-contained piece for one voice, with or without instrumental accompaniment, normally part of a larger work."));
            lst_Funds.add(new DModelDictionary(" Bagpipes ", "a musical instrument with reed pipes that produce sound resulted by the pressure of wind emitted from a bag squeezed by the player's arm. Bagpipes are associated especially with Scotland, but are also used in folk music in Ireland and France, and in varying forms across Europe and western Asia."));
            lst_Funds.add(new DModelDictionary(" Ballet ", "an artistic dance form performed to music, using precise and highly formalized set steps and gestures. Classical ballet, which originated in Renaissance Italy and established its present form during the 19th century, is characterized by light, graceful movements and the use of pointe shoes with reinforced toes."));
            lst_Funds.add(new DModelDictionary(" Baritone ", "an adult male singing voice between tenor and bass."));
            lst_Funds.add(new DModelDictionary(" Bass ", "the lowest adult male singing voice."));
            lst_Funds.add(new DModelDictionary(" Castrato ", "a male singer castrated in boyhood so as to retain a soprano or alto voice. The practice of castration was banned in 1903."));
            lst_Funds.add(new DModelDictionary(" Choir ", "an organized group of singers, especially one that takes part in church services or performs in public."));
            lst_Funds.add(new DModelDictionary(" Circular breathing ", "a technique of inhaling through the nose while blowing air through the lips from the cheeks, used to maintain constant exhalation, especially by players of certain wind instruments."));
            lst_Funds.add(new DModelDictionary(" Clarinet ", "a woodwind instrument with a single-reed mouthpiece, a cylindrical tube with a flared end, and holes stopped by keys."));
            lst_Funds.add(new DModelDictionary(" Composer ", "a person who writes music as a professional occupation."));
            lst_Funds.add(new DModelDictionary(" Concerto ", "a musical composition for a solo leading instrument(s) accompanied by an orchestra."));
            lst_Funds.add(new DModelDictionary(" Conductor ", "a person who directs the performance of an orchestra or choir, usually with the help of a baton (wooden stick)."));
            lst_Funds.add(new DModelDictionary(" Contrabass (or double bass) ", "the largest and lowest-pitched instrument of the strings family, providing the bassline of the orchestral string section and also used in jazz and some country music."));
            lst_Funds.add(new DModelDictionary(" Cymbal ", "percussion instrument of indefinite pitch (a sound which doesn’t correspond to a specific musical note). O\u001Fen used in pairs, cymbals consist of thin, normally round metal plates."));
            lst_Funds.add(new DModelDictionary(" Dodecaphony (or twelve-tone technique) ", "In this compositional system, the same note cannot be repeated before all others are played, like this, all notes have equal importance. It consists of using all of the 12 notes in the western chromatic scale system. This new revolutionary way of composing music was developed in the XXth century."));
            lst_Funds.add(new DModelDictionary(" Duduk ", "an ancient Armenian double reed woodwind instrument made of apricot wood. It is usually played in pairs, while one player plays the melody, another player holds a sustained low tone (dum). It has a soulful and evocative sound. It was used in the film “Gladiator” (2000), directed by Ridley Scott."));
            lst_Funds.add(new DModelDictionary(" Flute ", "a wind instrument made from a tube with holes that are covered by the fingers or keys, held vertically or horizontally so that the player's breath strikes a narrow edge. The modern orchestral form is a transverse flute, which is held horizontally, typically made of metal, with an elaborate set of keys. (Flutist - flute player)"));
            lst_Funds.add(new DModelDictionary(" Fantasia ", "musical composition with its roots in the art of improvisation. It seldom follows the textbook rules of any strict musical form."));
            lst_Funds.add(new DModelDictionary(" Horn ", "comes in various shapes. A tube, usually made of metal and o\u001Fen curved in various ways, with one narrow end into which the musician blows, and a wide end from which sound emerges. (Hornist - horn player)"));
            lst_Funds.add(new DModelDictionary(" Lute ", "a plucked string instrument with a long neck bearing frets and a rounded body with a flat front, rather like a halved egg in shape. An ancestor of the modern guitar. (Lutenist - lute player)"));
            lst_Funds.add(new DModelDictionary(" Luthier ", "symbols denoting a musical sound. Several systems are used nowadays, depending on the country. English speaking countries use the note names: A-B-C-D-E-F-G, while some other countries call them: Do-Re-Mi-Fa-Sol-La-Si."));
            lst_Funds.add(new DModelDictionary(" Movement ", "symbols denoting a musical sound. Several systems are used nowadays, depending on the country. English speaking countries use the note names: A-B-C-D-E-F-G, while some other countries call them: Do-Re-Mi-Fa-Sol-La-Si."));
            lst_Funds.add(new DModelDictionary(" Musical Notes ", "a pedestal or elevated rack designed to hold a paper score or sheets of music in position for reading."));
            lst_Funds.add(new DModelDictionary(" Musicologist ", "an expert in music as an academic subject, usually a non-performing musician."));
            lst_Funds.add(new DModelDictionary("Note", "a symbol denoting a musical sound. Notes can represent the pitch and duration of a sound in musical notation."));
            lst_Funds.add(new DModelDictionary("Opera", "a dramatic work in one or more acts, set to music for singers and instrumentalists."));
            lst_Funds.add(new DModelDictionary("Orchestra", "a group of instrumentalists, one that combines string, woodwind, brass, and percussion instruments, usually playing classical music."));
            lst_Funds.add(new DModelDictionary("Organ", "a large musical instrument having rows of pipes supplied with air from bellows (now usually electrically powered), and played using a keyboard or an automatic mechanism. The pipes are controlled by levers that open and close the air"));
            lst_Funds.add(new DModelDictionary("Piano", "a large keyboard musical instrument with a wooden case enclosing a soundboard and metal strings, which are struck by hammers when the keys are depressed. The strings' vibration is stopped by dampers when the keys are released and can be regulated for length and volume by two or three pedals."));
            lst_Funds.add(new DModelDictionary("Percussion", "musical instruments played by striking with the hand or with a stick or beater, or by shaking, including drums, cymbals, xylophones, gongs, bells, and rattles. (Percussionist - percussion player)"));
            lst_Funds.add(new DModelDictionary("Perfect Pitch", "the ability to recognize the pitch (degree of highness or lowness) of a note or produce any given note without having an external reference."));
            lst_Funds.add(new DModelDictionary("Premiere", "the debut (first public presentation) of a play, film, dance, or musical composition."));
            lst_Funds.add(new DModelDictionary("Prepared Piano", "a piano with objects placed on or between the strings, or with some retuned"));
            lst_Funds.add(new DModelDictionary("Presto", "(From italian) in a quick tempo, fast."));
            lst_Funds.add(new DModelDictionary("Quartet", "1. a musical composition for four instruments or voices. 2. a group or set of four musicians (ex. classical string quartet consists of two violinists, an violist and a cellist)."));
            lst_Funds.add(new DModelDictionary("Rhythm", "a strong, regular repeated pattern of movement or sound."));
            lst_Funds.add(new DModelDictionary("Rehearsal", "a practice or trial performance of a play, musical concert or other work for later public performance."));
            lst_Funds.add(new DModelDictionary("Sheet music/Scores", "written form of a musical composition."));
            lst_Funds.add(new  DModelDictionary("Soprano", "the highest singing voice, usually sung by women."));
            lst_Funds.add(new  DModelDictionary("String (Instruments)", "a family of musical instruments that produce sound from vibrating strings."));
            lst_Funds.add(new  DModelDictionary("String Quartet (Classical)", "a chamber music ensemble consisting of first and second violins, viola, and violoncello."));
            lst_Funds.add(new  DModelDictionary("Subito forte", "(from Italian) suddenly loud."));
            lst_Funds.add(new  DModelDictionary("Symphony", "an elaborate musical composition for orchestra, typically in four movements."));
            lst_Funds.add(new  DModelDictionary("Tempo", "the speed or pace of a given piece."));
            lst_Funds.add(new  DModelDictionary("Tessitura (or register)", "a particular part of the range of a voice or instrument."));
            lst_Funds.add(new  DModelDictionary("Theremin", "an electronic musical instrument controlled without physical contact by the player. It is named a\u001Eer its inventor, Leon Theremin, who patented the device in 1928."));
            lst_Funds.add(new  DModelDictionary("Trumpet", "a brass musical instrument with a flared bell and a bright, penetrating tone."));
            lst_Funds.add(new  DModelDictionary("Tune(or melody)", "The main part of a musical composition. It usually has an accompaniment (secondary part)."));
            lst_Funds.add(new  DModelDictionary("Tuning", "preparing the instrument so that when it is played it will sound at the correct pitch: not too high nor too low."));
            lst_Funds.add(new  DModelDictionary("Viola", "string instrument with a lower sound than the violin, but higher than the violoncello. In the past, the viola varied in size and style, as did in names. The word viola originates from the Italian language. The Italians o\u001Een used the term: viola da braccio (“of the arm”)."));
            lst_Funds.add(new  DModelDictionary("Violin", "a string musical instrument of high pitch, played with a horsehair bow. The classical European violin was developed in the 16th century. It has four strings and a body narrowed at the middle, with two f-shaped sound holes."));
            lst_Funds.add(new DModelDictionary("Violoncello (cello)", "a bass instrument of the string family, held upright on the floor between the legs of the seated player. It o\u001Een plays the bass part."));
            lst_Funds.add(new DModelDictionary("Wind (instruments)", "a family of musical instruments that produces sound by the vibration of air. Typically the players blow into the instrument using special techniques."));


            dictionaryRCVAdapter = new DictionaryRCVAdapter(getActivity(), lst_Funds, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            lsvDictionary.setLayoutManager(linearLayoutManager);
            lsvDictionary.setAdapter(dictionaryRCVAdapter);

        } else {
            dictionaryRCVAdapter.notifyDataSetChanged();
        }
    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DModelDictionary> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (DModelDictionary item : lst_Funds) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getWord().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found for word" + text, Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            dictionaryRCVAdapter.filterList(filteredlist);
//            Toast.makeText(getContext(), "Data Found.." + text, Toast.LENGTH_SHORT).show();
        }
    }
}
