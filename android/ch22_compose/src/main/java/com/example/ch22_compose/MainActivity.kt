package com.example.ch22_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch22_compose.ui.theme.MyProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {  // 화면 UI 작성
            MyProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by remember{ mutableStateOf("") }

    //나만의 modigier
    val mymodifier = Modifier
        .border(width=10.dp, color = Color.Red)
        .padding(all=30.dp)
        .background(Color.Cyan)
    Column(horizontalAlignment = ) { //수직
        Row(
            modifier = Modifier
                .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
        ) { //수평
            Text(
                text = "Hello $name!",
                modifier = mymodifier
            )
            Text(
                text = "Hello $name! 2",
                modifier = modifier
            )
        }
        Image(
            painter = painterResource(id=R.drawable.dog),
            contentDescription = "dog",
            modifier= Modifier.width(100.dp)
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            label={ Text("이름을 입력하세요") }
        )
        Button(
            onClick = {
                Log.d("25android", "$text")
            }
        ){
            Text("버튼")
        }
        LazyRow {
            val datas = listOf<String>("A", "B", "C")
            itemsIndexed(datas){
                index, item -> Text("아이템 $item")
            }
        }
        LazyColumn {
            val datas = listOf<String>("A", "B", "C")
            itemsIndexed(datas){
                    index, item -> Text("아이템 $item")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyProjectTheme {
        Greeting("Android")
    }
}