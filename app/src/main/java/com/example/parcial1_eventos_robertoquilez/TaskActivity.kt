package com.example.parcial1_eventos_robertoquilez

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskScreen()
        }
    }
}

@Composable
fun TaskScreen() {
    var taskText by remember { mutableStateOf("") }
    val pendingTasks = remember { mutableStateListOf<String>() }
    val doneTasks = remember { mutableStateListOf<String>() }
    var showPending by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        TextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text(stringResource(id = R.string.new_task)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (taskText.isNotEmpty()) {
                pendingTasks.add(taskText)
                taskText = ""
            }
        }) {
            Text(stringResource(id = R.string.add_task))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { showPending = true }) {
                Text(stringResource(id = R.string.show_pending))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { showPending = false }) {
                Text(stringResource(id = R.string.show_done))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(if (showPending) pendingTasks else doneTasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.LightGray)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    if (showPending) {
                        Button(onClick = {
                            pendingTasks.remove(task)
                            doneTasks.add(task)
                        }) {
                            Text(stringResource(id = R.string.done))
                        }
                    } else {
                        Button(onClick = {
                            doneTasks.remove(task)
                        }) {
                            Text(stringResource(id = R.string.delete))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}