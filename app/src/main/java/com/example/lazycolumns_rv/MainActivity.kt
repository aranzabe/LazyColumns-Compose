package com.example.lazycolumns_rv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazycolumns_rv.ui.theme.LazyColumns_RVTheme
import com.example.rvcompose.Modelo.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumns_RVTheme {
                Column() {
                    Spacer(modifier = Modifier.height(40.dp))
                    SimpleRecyclerView()
               // RVUsuariosColsSencillo()
               // RVUsuariosFilas()
//                RVUsuariosCols()
//                GridUsuarios()
//               RVUsuariosControlsView()
//                RVUsuariosSticky()
                }
            }
        }
    }
}
