package ueolot.com.ueolot.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ueolot.com.ueolot.Fragments.Fragment_Classificacio_Femeni;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Cronica;
import ueolot.com.ueolot.Fragments.Fragment_Llista_Player_Femeni;
import ueolot.com.ueolot.Fragments.Fragment_llista_noticies_primerEquip;

public class PagerAdapter_Femeni extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter_Femeni(FragmentManager fm, int NumOfTabs) {
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
                Fragment_Llista_Player_Femeni plantilla = new Fragment_Llista_Player_Femeni();
                return plantilla;
            case 3:
                Fragment_Classificacio_Femeni classificacio = new Fragment_Classificacio_Femeni();
                return classificacio;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}