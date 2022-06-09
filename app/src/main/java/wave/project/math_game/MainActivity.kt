package wave.project.math_game

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.TypedValue
import wave.project.math_game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val n=0

        var points =0
        var hearts = 3

        var value_clicked = 0
        var value_loaded =false


        var sums = mutableListOf<Int>()
        var factors = mutableListOf<Int>()
        var filling_list = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        var display1 = mutableListOf<Int>()


        var recently_loaded_button  = binding.button
        var symbols_sign = mutableListOf<String>("+","-","x","÷")

        val buttons_top_section = listOf<Button>(binding.button1,binding.button4,binding.button3,
            binding.button2,binding.button5,binding.button6,binding.button7,binding.button8,
            binding.button9,binding.button10,binding.button11,binding.button12,binding.button13,
            binding.button14,binding.button15)

        val buttons_bottom_section  = listOf<Button>(binding.button28,binding.button38,binding.button37,binding.button36,
            binding.button35,binding.button34,binding.button33,binding.button32,
            binding.button31,binding.button30)

        val Symbols = listOf<TextView>(binding.textView26,binding.textView28,
        binding.textView30,binding.textView32,binding.textView33)






        fun random (upper_limit:Int,lower_limit:Int = 0 ):Int {
            var diff = upper_limit - lower_limit
            var return_value = Math.floor(Math.random() * diff + lower_limit)

            return return_value.toInt()

        }

        fun convert (var_1:Int,var_2:Int,Operation:String):Int{
            var return1 =0

            if (Operation == "x"){
                return1 = var_1*var_2
            }else if(Operation == "-" ){
                return1 =  var_1 - var_2
            }else if(Operation == "÷"){
                return1 =var_1 /var_2
            }else if(Operation == "+"){
                return1 = var_1 + var_2
            }
            return return1
        }

        fun factor_of(num :Int){
            factors.clear()
            for (i in 1..num){
                if (num%i==0){
                    factors.add(i)
                }
            }
        }

        fun ready_sums(){
            symbols_sign.shuffle()
            var i = 4
            for (sym in Symbols){
                i-=1
                if (i==-1){
                    var placehold= symbols_sign[random(4)]
                    Symbols.elementAt(4).text = placehold
                    symbols_sign.add(placehold)
                    break
                }
                Symbols.elementAt(i).text = symbols_sign[i]
            }

            for (sign in symbols_sign){

                var var1 = 0
                var var2 = 0
                if(sign == "+" ) {
                    var1 = random(101)
                    var2 = random(101)
                }else if(sign == "-"){
                    var1 = random(101)
                    var2 = random(101,var1)
                }else if(sign == "x" ){
                    var1 = random(11)
                    var2 = random(11)
                }else if(sign == "÷" ){
                    var1 = random(60)
                    factor_of(var1)
                    var2 = factors.elementAt(random(factors.size))
                }
                sums.add(var1)
                sums.add(var2)
                sums.add(convert(var1,var2,sign))
            }

            filling_list = sums.toMutableList()

            for (i in 0..4){
                var n = 4-i
                var diff1 = random(3)
                var index2 = 3*n+diff1
                filling_list.removeAt(index2)
                display1.add(index2)

                buttons_top_section.elementAt(index2).text = sums.elementAt(index2).toString()
                buttons_top_section.elementAt(index2).isEnabled = false
                buttons_top_section.elementAt(index2).setTextColor(Color.BLACK)

            }


        }

        fun display_bottom_Buttons(){
            var i = 9
            filling_list.shuffle()
            for (button_obj in buttons_bottom_section){
                button_obj.text = filling_list.elementAt(i).toString()
                i-=1
            }
        }

        fun ready_top_buttons(){
            for (obj in buttons_top_section){
                obj.text = ""
            }

        }

        fun redisplay_top_buttons(){
            for (i in display1) {
                buttons_top_section.elementAt(i).text = sums.elementAt(i).toString()
            }
        }

        fun start(){
            ready_top_buttons()
            ready_sums()
            display_bottom_Buttons()
        }

        fun restart (){
            value_loaded = false
            start()
        }

        fun reset(){
            value_loaded = false
            ready_top_buttons()
            display_bottom_Buttons()
            redisplay_top_buttons()
        }

        fun end_screen(){

        }


        fun submit(){
            var value = 0
            try {
                for (i in 0..4) {

                    var var1 = buttons_top_section.elementAt(i).text.toString().toInt()
                    var var2 = buttons_top_section.elementAt(i + 1).text.toString().toInt()
                    var var3 = buttons_top_section.elementAt(i + 2).text.toString().toInt()
                    var symbol3 = Symbols.elementAt(i).text.toString()
                    var var_33 = convert(var1, var2, symbol3)
                    if (var_33 != var3) {
                        hearts -= 1
                        restart()
                        break
                    } else {
                        value += 1
                    }
                }
                if (hearts == 0) {
                    end_screen()
                }

                if (value == 4) {
                    points += 1
                    binding.button.text= points.toString()
                    restart()
                }
            }finally{
                n=1
            }
        }

        fun Listener_button( target_:Button){
            if (value_loaded){
                if (target_.text == "") {
                    target_.text = value_clicked.toString()
                    value_loaded = false
                    recently_loaded_button.text = ""
                    recently_loaded_button = binding.button
                }else{
                    var str = target_.text.toString()
                    recently_loaded_button.text = str
                    target_.text= value_clicked.toString()
                    value_clicked = 0
                    value_loaded = false
                }

            }else{
                if (target_.text != ""){
                    value_loaded = true
                    var str = target_.text.toString()
                    value_clicked = str.toInt()
                    recently_loaded_button = target_

                }
            }
        }

//        fun bottom_Listener( target_ :Button){
//            value_clicked = target_.text.toString().toInt()
//            //recently_loaded_button = target_
//
//
//        }


        for (i in 0..4){
            for (j in 0..2){
                val index_ = (3*i+j).toInt()
                val button_ = buttons_top_section.elementAt(index_)

                button_.text=""
                button_.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
                button_.setOnClickListener{Listener_button(button_)}
            }
        }


        for (button_obj in buttons_bottom_section){
            button_obj.text = "1"
            button_obj.setOnClickListener{Listener_button(button_obj)}
            button_obj.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)

        }

        binding.button.setOnClickListener{reset()}
        binding.button16.setOnClickListener{submit()}
        start()

    }
}