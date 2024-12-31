package onlytrade.app.ui.home.products.add

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import onlytrade.app.android.R
import onlytrade.app.ui.design.components.OTOutlinedTextField

class AddProductScreen : Screen {

    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val productGridState = rememberLazyGridState()
        val headerVisible = productGridState.canScrollBackward.not()

        Scaffold(topBar = {
            AnimatedVisibility(visible = headerVisible) {
                Column {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Icon(
                            modifier = Modifier.clickable { nav.pop() },
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(android.R.string.cancel)
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Add a New Trade Product",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
                        )


                    }

                    Spacer(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                }
            }
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var productTitle by remember { mutableStateOf(TextFieldValue()) }
                var productDesc by remember { mutableStateOf(TextFieldValue()) }
                val inputWrongError = false // TODO: condition from ViewModel
                Text(
                    text = "Product Name or Title",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500)
                )
                OTOutlinedTextField(
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                    value = productTitle,
                    onValueChange = { productTitle = it },
                    label = "Product Title",
                    isError = inputWrongError,
                    trailingIcon = {
                        if (productTitle.text.isNotBlank()) {
                            Icon(painter = painterResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { productTitle = TextFieldValue("") })
                        }
                    },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

                Text(
                    text = "Product Details",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500)
                )
                OTOutlinedTextField(
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W500),
                    value = productDesc,
                    onValueChange = { productDesc = it },
                    label = "Product Details",
                    isError = inputWrongError,
                    trailingIcon = {
                        if (productDesc.text.isNotBlank()) {
                            Icon(painter = painterResource(R.drawable.outline_clear_24),
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = null,
                                modifier = Modifier.clickable { productDesc = TextFieldValue("") })
                        }
                    },
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp),
                    text = "Add Product Pictures",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = W700)
                )
                val size = (LocalConfiguration.current.screenWidthDp / 4).dp
                val context = LocalContext.current

                Icon(
                    Icons.Filled.Add,
                    "Add Product",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            Toast
                                .makeText(context, "clicked", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .background(
                            shape = MaterialTheme.shapes.large,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        .size(size)
                )
            }
        }


    }

    @Composable
    private fun TakePhotoFromPhone() {
        val context = LocalContext.current

        // launches photo picker
        val pickImageFromAlbumLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { urls ->
           // viewModel.onReceive(Intent.OnFinishPickingImagesWith(currentContext, urls))
        }

        // launches camera
        val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
            if (isImageSaved) {
               // viewModel.onReceive(Intent.OnImageSavedWith(currentContext))
            } else {
                // handle image saving error or cancellation
             //   viewModel.onReceive(Intent.OnImageSavingCanceled)
            }
        }

        // launches camera permissions
        val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
               // viewModel.onReceive(Intent.OnPermissionGrantedWith(currentContext))
            } else {
                // handle permission denied such as:
               // viewModel.onReceive(Intent.OnPermissionDenied)
            }
        }
    }


 /*   @Composable
    private fun SubCategoryUI(index: Int) {
        val size = (LocalConfiguration.current.screenWidthDp / 2).dp
        val nav = LocalNavigator.currentOrThrow
        Column(modifier = Modifier.clickable {
            nav.push(ProductsScreen("Subcategory ${index + 1}"))
        }) {
            Box(
                Modifier
                    .size(size)
                    .background(
                        color = Color(
                            Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
                        ), shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Text(
                text = "Subcategory ${index + 1}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = W700)
            )


        }
    }*/
}