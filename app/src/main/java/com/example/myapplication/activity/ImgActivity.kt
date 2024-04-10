package com.example.myapplication.activity


import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import java.util.ArrayList

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    var isExpanded = false;
    // 创建一个能够根据 isExpanded 变量值而改变颜色的变量
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded) Color(0xFFCCCCCC) else MaterialTheme.colorScheme.surface,
        label = ""
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(all = 8.dp)
            .clickable {
                isExpanded = !isExpanded
            },
        color = surfaceColor
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Image(
                painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.background, shape = CircleShape)
            )
            Spacer(Modifier.padding(horizontal = 8.dp))
            Column {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.padding(vertical = 4.dp))
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    MaterialTheme {
        var msgs = ArrayList<Message>();
        msgs.add(Message("你好", "我不好"))
        msgs.add(Message("你好", "我很好"))
        Conversation(
            messages = msgs
        )
    }
}

