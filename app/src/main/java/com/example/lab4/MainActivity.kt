package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab4.ui.theme.Lab4Theme
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            Lab4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {

    var amountInput by remember { mutableStateOf("") }

    var tipInput by remember { mutableStateOf("") }

    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0

    val tipPercent = tipInput.replace(',', '.').toDoubleOrNull() ?: 15.0

    val tip = calculateTip(
        amount = amount,
        tipPercent = tipPercent,
        roundUp = roundUp
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4FB))
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(horizontal = 28.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        // Tiêu đề
        Text(
            text = "Calculate Tip",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF234B63),
            modifier = Modifier.padding(bottom = 28.dp)
        )

        // Ô nhập Bill Amount
        TextField(
            value = amountInput,
            onValueChange = {
                amountInput = it
            },
            label = {
                Text("Bill Amount")
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ô nhập Tip Percentage
        TextField(
            value = tipInput,
            onValueChange = {
                tipInput = it
            },
            label = {
                Text("Tip Percentage (%)")
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Round Up
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 14.dp
                ),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Round Up Tip",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF234B63)
            )

            Switch(
                checked = roundUp,
                onCheckedChange = {
                    roundUp = it
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Kết quả
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFD4EAF7),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Tip Amount",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF426579)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tip,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D5878)
            )
        }
    }
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double,
    roundUp: Boolean
): String {

    var tip = tipPercent / 100 * amount

    if (roundUp) {
        tip = ceil(tip)
    }

    return NumberFormat
        .getCurrencyInstance(Locale.US)
        .format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {

    Lab4Theme {
        TipTimeLayout()
    }
}