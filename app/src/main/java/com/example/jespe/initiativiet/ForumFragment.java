package com.example.jespe.initiativiet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ForumFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    FloatingActionButton fab;
    SearchView search;
    ListView debat;
    DatabaseReference fb;
    ValueEventListener vEL;
    ArrayAdapter aa;

    Map<String, String> map;
    ArrayList<Lovforslag> lfs;

    //Lovforslag[] lfs = {
    //        new Lovforslag("Sammy", "Vestlige invandrer", "Integrations tekst", "Jeg mangler integration... bla bla bla"),
    //        new Lovforslag("Niklas", "Højere eller lavere SU?", "Uddannelse", "Højere eller lavere SU? Giv jeres holdninger til kende"),
    //        new Lovforslag("Jonathan", "Ikke-vestlige", "Integration", "Jeg synes, at..."),
    //        new Lovforslag("Gustav", "Bitcoins pristigninger", "Finans", "Er det værd at investere i bitcoins?"),
    //        new Lovforslag("Jesper", "Knallert stier", "Trafik", "Jeg synes os på knallerter skal have \"cykelstier\" ")
    //};

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = i.inflate(R.layout.fragment_forum, container, false);

        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

            fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setOnClickListener(this);
            debat = (ListView) view.findViewById(R.id.debat);

            lfs = new ArrayList<>();

            //Firebase Reference
            fb = FirebaseDatabase.getInstance().getReference().child("forumentries");
            vEL = fb.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lfs.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        map = (Map) snapshot.getValue();
                        lfs.add(new Lovforslag(
                                map.get("title"),
                                map.get("content"),
                                map.get("tagName"),
                                map.get("type"),
                                snapshot.getKey()
                        ));

                    }
                    debat.setAdapter(aa);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            aa = new ArrayAdapter(getActivity(), R.layout.postlist, R.id.headline, lfs) {

                @Override
                public View getView(int position, View cachedView, ViewGroup parent) {

                    View v = super.getView(position, cachedView, parent);
                    if (position != lfs.size()) {
                        TextView headline = (TextView) v.findViewById(R.id.headline);
                        TextView category = (TextView) v.findViewById(R.id.category);
                        TextView description = (TextView) v.findViewById(R.id.description);
                        //TextView author = (TextView) v.findViewById(R.id.author);

                        headline.setText(lfs.get(position).getTitle());
                        category.setText(lfs.get(position).getTagName());
                        description.setText(lfs.get(position).getContent());
                        // author.setText(lfs.get(position).getAuthor());

                        switch (lfs.get(position).getTagName()) {
                            case "Miljø":
                                //parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                                break;
                            case "Finans":
                                //parent.getChildAt(position).setBackgroundColor(Color.BLUE);
                                break;
                            case "Integration":
                                //parent.getChildAt(position).setBackgroundColor(Color.RED);
                                break;
                            case "Uddannelse":
                                //parent.getChildAt(position).setBackgroundColor(Color.YELLOW);
                                break;
                            default:
                                //parent.getChildAt(position).setBackgroundColor(Color.GRAY);
                                break;
                        }

                        //return v;
                    }

                    return v;
                }
            };

            //debat = new ListView(this);
            debat.setOnItemClickListener(this);
            //debat.setAdapter(aa);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                createPost();
                break;
        }

    }

    public void onItemClick(AdapterView<?> I, View v, int position, long id) {
        //Creating fragment and bundle for carrying data
        Fragment frag = new ForumItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Key", lfs.get(position).getKey());
        frag.setArguments(bundle);

        /*
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, frag);
        transaction.addToBackStack(null);
        transaction.commit();
        */

        //Hack mode
        getFragmentManager().beginTransaction()
                .add(R.id.frame, frag, "ForumItemFrag")
                .addToBackStack("TabFrag")
                .commit();
    }

    public void createPost() {

        //Hack mode
        getFragmentManager().beginTransaction()
                .add(R.id.frame, new CreatePostFragment(), "CreatePost")
                .addToBackStack("TabFrag")
                .commit();
        /*
        getFragmentManager().beginTransaction()
                .replace(R.id.frame, new CreatePostFragment())
                .addToBackStack(null)
                .commit();
        */
    }
}