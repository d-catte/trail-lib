package io.github.d_catte.data;

import com.google.gson.annotations.SerializedName;

public enum ScreenType {
    @SerializedName("shop")
    SHOP,
    @SerializedName("landmark")
    LANDMARK,
    @SerializedName("trading")
    TRADING,
    @SerializedName("travel")
    TRAVEL,
    @SerializedName("accident")
    ACCIDENT,
    @SerializedName("hunting")
    HUNTING,
    @SerializedName("indian")
    INDIAN_ATTACK,
    @SerializedName("river")
    RIVER_CROSSING
}
