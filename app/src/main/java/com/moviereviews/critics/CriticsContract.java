package com.moviereviews.critics;


import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Critic;

import java.util.List;

public interface CriticsContract {

    interface View extends BaseView<Presenter> {
        void setData(List<Critic> critics);
    }

    interface Presenter{
        void setToFirstPage();
        void getCritics();
        void setCritics(List<Critic> critics);
        void getSearchByName(String name);
    }


}
