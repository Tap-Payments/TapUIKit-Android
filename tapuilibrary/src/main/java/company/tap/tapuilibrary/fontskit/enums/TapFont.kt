package company.tap.tapuilibrary.fontskit.enums


/**

Copyright (c) 2020    Tap Payments.
All rights
reserved.
 **/
enum class TapFont {
    helveticaNeueThin,
    HelveticaNeueLight,
    HelveticaNeueMedium,
    HelveticaNeueRegular,
    HelveticaNeueBold,

    RobotoThin,
    RobotoLight,
    RobotoMedium,
    RobotoRegular,
    RobotoBold,

    TajawalThin,
    TajawalLight,
    TajawalMedium,
    TajawalRegular,
    TajawalBold,


    CirceExtraLight,
    CirceLight,
    CirceRegular,
    CirceBold,

    ArabicHelveticaNeueLight,
    ArabicHelveticaNeueRegular,
    ArabicHelveticaNeueBold,

    SystemDefault;



    companion object {
        fun tapFontType(tapFont: TapFont):String {
            return when(tapFont){
                RobotoRegular -> "fonts/Roboto-Regular.ttf"
                RobotoThin ->"fonts/Roboto-Thin.ttf"
                RobotoLight->"fonts/Roboto-Light.ttf"
                RobotoMedium->"fonts/Roboto-Medium.ttf"
                RobotoBold->"fonts/Roboto-Bold.ttf"

                TajawalRegular -> "fonts/Tajawal-Bold.ttf"
                TajawalThin ->"fonts/Tajawal-Thin.ttf"
                TajawalLight->"fonts/Tajawal-Light.ttf"
                TajawalMedium->"fonts/Tajawal-Medium.ttf"
                TajawalBold->"fonts/Tajawal-Bold.ttf"

                CirceExtraLight->"fonts/Circe-ExtraLight.ttf"
                CirceLight->"fonts/Circe-Light.ttf"
                CirceRegular->"fonts/Circe-Regular.ttf"
                CirceBold->"fonts/Circe-Bold.ttf"
                HelveticaNeueBold->"fonts/HelveticaNeueLTW20-Bold.ttf"
                HelveticaNeueLight->"fonts/HelveticaNeueLTW20-Light.ttf"
                HelveticaNeueRegular->"fonts/HelveticaNeueLTW20-Roman.ttf"
               else->"fonts/Roboto-Regular.ttf"
            }
        }
    }


}





