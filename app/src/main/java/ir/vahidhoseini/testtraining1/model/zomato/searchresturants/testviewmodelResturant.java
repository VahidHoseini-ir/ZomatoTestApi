package ir.vahidhoseini.testtraining1.model.zomato.searchresturants;

import java.util.List;

public class testviewmodelResturant {

    private List<restaurant> restaurant;

    public class restaurant {
        private String id;
        private String name;
        private String cuisines;
        private String timings;
        private int average_cost_for_two;
        private String currency;
        private String[] highlights;
        private String featured_image;
        private String phone_numbers;
        private Location location;

        public class Location {
            private String address;
            private String locality;
            private String city;
            private String latitude;
            private String longitude;
        }


    }
}

