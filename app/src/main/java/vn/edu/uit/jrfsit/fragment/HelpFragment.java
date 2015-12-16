// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import vn.edu.uit.jrfsit.R;

// Referenced classes of package vn.edu.uit.jrfsit.fragment:
//            BaseFragment

public class HelpFragment extends BaseFragment {
    View v;
    FloatingActionButton btChooseHelp;
    SubActionButton btHelpSearch;
    SubActionButton btHelpSaveJob;
    SubActionButton btHelpRecJob;
    SubActionButton btHelpProfile;
    SubActionButton btHelpSetting;
    FloatingActionMenu actionMenu;

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        v = layoutinflater.inflate(R.layout.help_fragment, viewgroup, false);
        load();
        return v;
    }

    private void load() {
        super.loadActivity(R.string.title_activity_help);
        initControlOnView();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(activity);

        ImageView itemIconSearch = new ImageView(activity);
        itemIconSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
        btHelpSearch = itemBuilder.setContentView(itemIconSearch).build();

        ImageView itemIconSaveJob = new ImageView(activity);
        itemIconSaveJob.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_job));
        btHelpSaveJob = itemBuilder.setContentView(itemIconSaveJob).build();

        ImageView itemIconRecJob = new ImageView(activity);
        itemIconRecJob.setImageDrawable(getResources().getDrawable(R.drawable.ic_recommended));
        btHelpRecJob = itemBuilder.setContentView(itemIconRecJob).build();

        ImageView itemIconProfille = new ImageView(activity);
        itemIconProfille.setImageDrawable(getResources().getDrawable(R.drawable.ic_profile));
        btHelpProfile = itemBuilder.setContentView(itemIconProfille).build();

        ImageView itemIconSetting = new ImageView(activity);
        itemIconSetting.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_manage));
        btHelpSetting = itemBuilder.setContentView(itemIconSetting).build();

        actionMenu = new FloatingActionMenu.Builder(activity)
                .addSubActionView(btHelpSearch, 200,200)
                .addSubActionView(btHelpSaveJob,200,200)
                .addSubActionView(btHelpRecJob,200,200)
                .addSubActionView(btHelpProfile,200,200)
                .addSubActionView(btHelpSetting,200,200)
                .setRadius(450)
                .attachTo(btChooseHelp)
                .build();
        initListener();
        setFragment(new HelpSearchFragment());
    }

    public void initControlOnView() {
        btChooseHelp = (FloatingActionButton) v.findViewById(R.id.bt_choose_help);
    }

    public void initListener() {
        btHelpSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new HelpSearchFragment());
                actionMenu.close(true);
            }
        });

        btHelpSaveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new HelpSaveFragment());
                actionMenu.close(true);
            }
        });

        btHelpRecJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new HelpRecFragment());
                actionMenu.close(true);
            }
        });

        btHelpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new HelpProfileFragment());
                actionMenu.close(true);
            }
        });

        btHelpSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new HelpSettingFragment());
                actionMenu.close(true);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.help_fragment, fragment)
                .commit();
    }
}
