package odu.handson.classquiz.UI;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import odu.handson.classquiz.R;


public class CompletedquizActivity extends AppCompatActivity {
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    String quizId,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedquiz);


        quizId=getIntent().getStringExtra("quizId");
        userId=getIntent().getStringExtra("userId");

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),2);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("Quiz");
        tabLayout.getTabAt(1).setText("Reports");


    }

    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(getApplicationContext(),DisplayquizActivity.class);
        in.putExtra("userId",getIntent().getStringExtra("userId"));
        startActivity(in);
    }
    class PagerAdapter extends FragmentPagerAdapter {
        int mNumOfTabs;
        public PagerAdapter(FragmentManager fm,int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            Bundle bundle=new Bundle();
            bundle.putString("userId",userId);
            bundle.putString("quizId",quizId);

            System.out.println("position fragment--"+position);
            if(position==0) {
                fragment = new CompletedquizFragment();
            }else if(position==1)
            {
                fragment=new ReportsFragment();

            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return  mNumOfTabs;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
