package com.example.composetutorialkt

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorialkt.ui.theme.ComposeTutorialKtTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialKtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
//                    color = MaterialTheme.colorScheme.background
                ) {
                    MessageCard(Message("나다", "너다"))
                }
            }
        }
    }
}
data class Message(val author:String, val body:String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(2.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

// Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false)}
        //remember 를 사용하여 메모리에 로컬 상태를 저장
        //mutableStateOf 에 전달된 값의 변경사항을 추적할 수 있습니다. 이 상태를 사용하는 컴포저블
        // 및 하위 요소는 값이 없데이트되면 자동으로 다시 그려집니다.

        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        Column (modifier = Modifier.clickable { isExpanded =! isExpanded }){
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
// Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
//                shape = MaterialTheme.shapes.medium
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all=4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
//                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }
    }

}
@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages){message ->
            MessageCard(message)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false, name = "DarK Mode")
@Preview(name = "light Mode", showBackground = true)

@Preview(showBackground = true)
@Composable
fun PreviewMessageCard() {
    ComposeTutorialKtTheme() {
//        MessageCard(
//            msg = Message("드가자", "출발하자")
//        )
        Conversation(SampleData.conversationSample)
    }
}

