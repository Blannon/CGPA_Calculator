package com.example.cgpacalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpacalculator.ui.theme.CGPACalculatorTheme

data class Semester(val grade: String, val credit: Int)
class MainActivity : ComponentActivity() {
    private var semesters:MutableList<Semester> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGPACalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CGPA(semesters)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RememberReturnType")
@Composable

fun CGPA(semesters : MutableList<Semester>){
    var grade1 by remember { mutableStateOf("") }
    var credit1 by remember { mutableStateOf<Int?>(null) }
    var grade2 by remember { mutableStateOf("") }
    var credit2 by remember { mutableStateOf<Int?>(null) }
    var grade3 by remember { mutableStateOf("") }
    var credit3 by remember { mutableStateOf<Int?>(null) }
    var grade4 by remember { mutableStateOf("") }
    var credit4 by remember { mutableStateOf<Int?>(null) }

    var cgpa by remember { mutableStateOf(0.0)    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)) {
        Text(
            text = " CGPA Grading System \n Blannon Network Academy ",
            modifier = Modifier.fillMaxWidth()
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    initialDelayMillis = 0,
                    velocity = 100.dp
                ),

            style = TextStyle(
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.audela_bold)),
                color = Color(0xff000000)
            ),

        )
    Spacer(modifier = Modifier.padding(
        top = 12.dp,
        ))
    SubjectText(subject = "Mathematics")
        GradeTextField(grade1) {grade1= it}
        Spacer5()
        CreditTextField (credit1) {credit1 = it}

        Spacer5()

        SubjectText(subject = "Computer Studies")
        GradeTextField (grade2) {grade2 = it}
        Spacer5()
        CreditTextField(credit2) {credit2 = it}
        Spacer5()

        SubjectText(subject = "Biology")
        GradeTextField (grade3) {grade3 = it}
        Spacer5()
        CreditTextField(credit3) {credit3 = it}

        SubjectText(subject = "Physics")
        GradeTextField (grade4) {grade4 = it}
        Spacer5()
        CreditTextField(credit4) {credit4 = it}
        Spacer5()

        Row{
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween) {
                Button(onClick ={

                                val semester = Semester(grade1, credit1 ?: 0)
                    semesters.add(semester)
                    val totalCredit = semesters.sumOf { it. credit }
                    val totalGradePoint = semesters.sumOf { gradePoints(it.grade, it.credit) }
                    cgpa = if (totalCredit > 0){
                        totalGradePoint / totalCredit.toDouble()
                    } else 0.0


                    grade1 = " "
                    credit1 = null
                    grade2 = " "
                    credit2 = null
                    grade3 = " "
                    credit3 = null
                    grade4 = " "
                    credit4 = null

                },
                    colors = ButtonDefaults.buttonColors(Color.Magenta ),
                    shape = RoundedCornerShape(15.dp))
                {
                    Text(
                        text = "Calculate CGPA",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.audela_semibold))
                    )
                }
                Surface(
                    modifier = Modifier
                        .width(160.dp)
                        .wrapContentHeight(),
                    color = Color.Gray,
                    shape = RoundedCornerShape(15.dp),

                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Your All \n CGPA is $cgpa ",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.audela_semibold)),
                            fontSize = 16.sp,
                            color = Color.Black
                        ))
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                color = Color.Gray,
                shape = RoundedCornerShape(15.dp),

                ) {

                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        text = "Previous Semester ",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.audela_semibold)),
                            fontSize = 16.sp,
                            color = Color.Black
                        ))
                    if (semesters.isEmpty()) {
                        // Handle the case when the 'semesters' list is empty
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "No semesters found",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.audela_semibold)),
                            fontSize = 16.sp
                        )
                    } else {
                        // Iterate through 'semesters' and display the information for each semester
                        for (semester in semesters) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Grade: ${semester.grade}\nCredit: ${semester.credit}",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.audela_semibold)),
                                fontSize = 16.sp
                            )
                        }
                    }



                }

            }
        }
    }
}

fun gradePoints(grade: String, credit: Int) : Double {

    val gradePoint = when(grade) {
        "A" -> 10.0
        "B" -> 9.0
        "C" -> 8.0
        "D" -> 7.0
        "E" -> 6.0
        "F" -> 5.0
        else -> 0.0
    }

    return gradePoint * credit
}


@Composable
    fun Spacer5(){
        Spacer(modifier = Modifier.padding(4.dp))
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable

    fun GradeTextField(grade:String, onValueChange:(String)-> Unit){
        TextField(value = grade, onValueChange = {text ->
            onValueChange(text)

        }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            label = {Text(text = "Enter Grade", color = Color.White, fontSize = 12.sp)},
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.DarkGray,
                textColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable

    fun CreditTextField(credit: Int?, onValueChange:(Int?)-> Unit){
        TextField(
            value = credit?.toString()?: "",
            onValueChange = {text ->
            onValueChange(text.toIntOrNull())

        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            label = {
                Text(
                    text = "Enter Credit",
                    color = Color.White,
                    fontSize = 12.sp)
                    },

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Gray,

            ),
            shape = RoundedCornerShape(15.dp),
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = Color.Black,
            ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

    }

@Composable

    fun SubjectText(subject: String){
    Text(
        text =subject,
        modifier = Modifier.fillMaxWidth(),

        style = TextStyle(
            fontSize = 16.sp,
            fontFamily =  FontFamily(Font(R.font.audela_semibold))
        )
    )
}



//@Preview(showBackground = true, showSystemUi = true )
//
//@Composable
//
//    fun CGPAPreview(){
//    CGPA( )
//}

