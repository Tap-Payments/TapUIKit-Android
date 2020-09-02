package company.tap.tapuilibrary.fontskit.enums


/**
 * Created by AhlaamK on 6/17/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
enum class TapFont {
    helveticaNeueThin,
    helveticaNeueLight,
    helveticaNeueMedium,
    helveticaNeueRegular,
    helveticaNeueBold,

    robotoThin,
    robotoLight,
    robotoMedium,
    robotoRegular,
    robotoBold,

    tajawalThin,
    tajawalLight,
    tajawalMedium,
    tajawalRegular,
    tajawalBold,


    circeExtraLight,
    circeLight,
    circeRegular,
    circeBold,

    arabicHelveticaNeueLight,
    arabicHelveticaNeueRegular,
    arabicHelveticaNeueBold,

    systemDefault;



    companion object {
        fun tapFontType(tapFont: TapFont):String {
            return when(tapFont){
                robotoRegular -> "assets/fonts/Roboto-Regular.ttf"
                robotoThin ->"assets/fonts/Roboto-Thin.ttf"
                robotoLight->"assets/fonts/Roboto-Light.ttf"
                robotoMedium->"assets/fonts/Roboto-Medium.ttf"
                robotoBold->"assets/fonts/Roboto-Bold.ttf"

                tajawalRegular -> "assets/fonts/Tajawal-Bold.ttf"
                tajawalThin ->"assets/fonts/Tajawal-Thin.ttf"
                tajawalLight->"assets/fonts/Tajawal-Light.ttf"
                tajawalMedium->"assets/fonts/Tajawal-Medium.ttf"
                tajawalBold->"assets/fonts/Tajawal-Bold.ttf"

                circeExtraLight->"assets/fonts/Circe-ExtraLight.ttf"
                circeLight->"assets/fonts/Circe-Light.ttf"
                circeRegular->"assets/fonts/Circe-Regular.ttf"
                circeBold->"assets/fonts/Circe-Bold.ttf"
                helveticaNeueBold->"assets/fonts/HelveticaNeueLTW20-Bold.ttf"
                helveticaNeueLight->"assets/fonts/HelveticaNeueLTW20-Light.ttf"
                helveticaNeueRegular->"assets/fonts/HelveticaNeueLTW20-Roman.ttf"
               else->"assets/fonts/Roboto-Regular.ttf"
            }
        }
    }


}





