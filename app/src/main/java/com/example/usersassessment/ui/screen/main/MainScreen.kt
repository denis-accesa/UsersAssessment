package com.example.usersassessment.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.usersassessment.R
import com.example.usersassessment.domain.model.User
import com.example.usersassessment.ui.theme.LocalDimensions

@Composable
fun MainScreenBridge() {

    MainScreen()
}

@Composable
fun MainScreen() {
    val dimensions = LocalDimensions.current

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = dimensions.spaceMd, vertical = dimensions.spaceXl), topBar = {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Search Users") },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(dimensions.spaceLg),
                    tint = Color(0x51206D0E),
                    painter = painterResource(id = R.drawable.ic_users),
                    contentDescription = ""
                )
            },
            colors = TextFieldDefaults.colors(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }) { innerPadding ->
        UsersGrid(
            users = listOf(User(1, "Alex", "dsaas")),
            innerPadding = innerPadding
        )
    }
}

@Composable
fun UsersGrid(
    users: List<User>,
    innerPadding: PaddingValues
) {
    val scrollState = rememberLazyStaggeredGridState()
    val dimensions = LocalDimensions.current

    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(innerPadding),
        verticalItemSpacing = dimensions.spaceSm,
        horizontalArrangement = Arrangement.spacedBy(dimensions.spaceSm),
        state = scrollState,
        columns = StaggeredGridCells.Fixed(2),
    ) {
        items(users, key = { it.id }) {
            UserCardItem(name = it.name, urlImage = it.imageUrl)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserCardItem(
    name: String,
    urlImage: String?,
) {
    val dimensions = LocalDimensions.current

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(dimensions.spaceXxs)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0x51206D0E))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            urlImage?.let {
                GlideImage(
                    model = urlImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
            Text(
                text = name,
                modifier = Modifier
                    .padding(LocalDimensions.current.spaceSm),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )
        }
    }
}


