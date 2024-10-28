package com.example.parcial1_eventos_robertoquilez

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
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
    var taskPriority by remember { mutableStateOf(false) }
    var taskDate by remember { mutableStateOf("") }
    val pendingTasks = remember { mutableStateListOf<String>() }
    val doneTasks = remember { mutableStateListOf<String>() }
    var showPending by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFADD8E6))
    ) {
        TextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text(stringResource(id = R.string.new_task)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDialog = true }) {
            Text(stringResource(id = R.string.add_task_details))
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(stringResource(id = R.string.go_to_main_screen))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(id = R.string.task_details)) },
            text = {
                Column {
                    TextField(
                        value = taskDate,
                        onValueChange = { taskDate = it },
                        label = { Text(stringResource(id = R.string.task_date)) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = taskPriority,
                            onCheckedChange = { taskPriority = it }
                        )
                        Text(stringResource(id = R.string.task_priority))
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (taskText.isNotEmpty()) {
                        val taskDetails = "$taskText - ${if (taskPriority) "High Priority" else "Normal Priority"} - $taskDate"
                        pendingTasks.add(taskDetails)
                        taskText = ""
                        taskPriority = false
                        taskDate = ""
                        showDialog = false
                    }
                }) {
                    Text(stringResource(id = R.string.add_task))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}
