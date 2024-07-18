package com.items.bim.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.items.bim.R
import com.items.bim.common.consts.StyleCommon
import com.items.bim.common.consts.SystemApp
import com.items.bim.common.ui.MySwipeRefresh
import com.items.bim.common.ui.MySwipeRefreshState
import com.items.bim.common.ui.NORMAL
import com.items.bim.common.util.Utils
import com.items.bim.dto.FileEntity
import com.items.bim.viewmodel.ImageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesSelectTop(
    titleValue: () -> String,
    onClose: () -> Unit = {},
    onExpand: (Boolean) -> Unit = {}
) {
    var expend by remember {
        mutableStateOf(false)
    }
    TopAppBar(title = {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = {
                expend = !expend
                onExpand(expend)
            }) {
                Row {
                    Text(text = titleValue())
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


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ImageSelect(imageViewModel: ImageViewModel, onClose: () -> Unit = {}, onSelect: (FileEntity) ->Unit =  {}) {
    var expend by remember {
        mutableStateOf(false)
    }
    val imagesGroups = imageViewModel.dirList
    var imagesGroup by remember {
        mutableStateOf<FileEntity>(FileEntity())
    }
    val path = imageViewModel.groupPath
    val images = imageViewModel.getImageList(path)
    var title by remember {
        mutableStateOf("最近照片")
    }
    val isDrawing by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val message = stringResource(id = R.string.empty_ui)
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = SystemApp.snackBarHostState,
                modifier = Modifier.padding(0.dp)
            )
        },
        topBar = {
            ImagesSelectTop(titleValue = {title}, onClose = onClose, onExpand = {
                expend = it
            })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                TextButton(onClick = {}) {
                    Text(text = "预览")
                    Utils.message(scope, message, SystemApp.snackBarHostState)
                }
                TextButton(onClick = {
                    Utils.message(
                        scope,
                        message,
                        SystemApp.snackBarHostState
                    )
                }) {
                    Text(text = "编辑")
                }
                RadioButton(selected = isDrawing, onClick = {
//                    isDrawing = !isDrawing
                    Utils.message(scope, message, SystemApp.snackBarHostState)
                })
                Text(text = "原图")
            }
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        if (expend) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                items(imagesGroups.size){it ->
                    val image = imagesGroups[it]
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                imagesGroup = image
                                title = image.name
                            },
                            shape = StyleCommon.ZERO_SHAPE,
                            colors = ButtonDefaults.buttonColors(Color.White)
                        ) {
                            AsyncImage(
                                image.location,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp)
                            )
                            Text(text = image.name)
                            Text(text = "(${image.dirSize})")
                        }
                    }
                }
            }
        } else {
            PhotoDataSetBody(
                images,
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
            ){
                onSelect(it)
                onClose()
            }
        }
    }

}