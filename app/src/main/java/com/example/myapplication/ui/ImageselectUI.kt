package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.common.consts.StyleCommon
import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.common.ui.MySwipeRefresh
import com.example.myapplication.common.ui.MySwipeRefreshState
import com.example.myapplication.common.ui.NORMAL
import com.example.myapplication.common.util.MediaResource
import com.example.myapplication.common.util.MediaStoreUtils
import com.example.myapplication.entity.ImageEntity
import com.example.myapplication.viewmodel.ImageViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ImagesSelectTop(onClose: () -> Unit = {}, onExpand: (Boolean) -> Unit = {}) {
    var expend by remember {
        mutableStateOf(false)
    }
    TopAppBar(title = {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                expend = !expend
                onExpand(expend)
            }) {
                Row {
                    Text(text = "最近照片")
                    Icon(
                        imageVector = if (!expend) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                        contentDescription = null
                    )
                }
            }
        }

    }, navigationIcon = {
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
    })
}


@Composable
@Preview
fun ImagesSelectBar() {


}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ImageSelect(imageViewModel: ImageViewModel, onClose : () -> Unit = {}) {
    val state = MySwipeRefreshState(NORMAL)
    val scope = rememberCoroutineScope()
    var expend by remember {
        mutableStateOf(false)
    }
    val imagesGroups = imageViewModel.dirList
    var imagesGroup by remember {
        mutableStateOf<ImageEntity>(imagesGroups[0])
    }
    Scaffold(
        topBar = {
            ImagesSelectTop(onClose=onClose, onExpand = {
                expend = it
            })
        },
        bottomBar = { ImagesSelectBar() },
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = expend, modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                for (image in imagesGroups) {
                    Row {
                        Button(
                            onClick = {
                                imagesGroup = image
                            },
                            shape = StyleCommon.ZERO_SHAPE,
                            colors = ButtonDefaults.buttonColors(Color.White)
                        ) {
                            Image(
                                rememberAsyncImagePainter(image.location),
                                contentDescription = null,
                                modifier = Modifier.height(80.dp).width(80.dp)
                            )
                            Text(text = image.name)
                            Text(text = "(${image.dirSize})")
                        }
                    }
                }
            }
        }

        MySwipeRefresh(state = state, onRefresh = { /*TODO*/ }, onLoadMore = { /*TODO*/ },
            modifier = Modifier.padding(it)
        ) {

        }
    }

}