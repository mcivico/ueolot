package ueolot.com.ueolot.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ueolot.com.ueolot.Fragments.Fragment_Calendari;
import ueolot.com.ueolot.Fragments.Fragment_Clasificacio;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Cronica;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Player;
import ueolot.com.ueolot.Fragments.Fragment_llista_noticies_primerEquip;

public class PagerAdapter_PrimerEquip extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter_PrimerEquip(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_llista_noticies_primerEquip noticies = new Fragment_llista_noticies_primerEquip();
                return noticies;
            case 1:
                Fragment_Llista_Cronica croniques = new Fragment_Llista_Cronica();
                return croniques;
            case 2:
                Fragment_Calendari calendari = new Fragment_Calendari();
                return calendari;
            case 3:
                Fragment_Llista_Player plantilla = new Fragment_Llista_Player();
                return plantilla;
            case 4:
                Fragment_Clasificacio clasificacio = new Fragment_Clasificacio();
                return clasificacio;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}