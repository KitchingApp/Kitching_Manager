package com.kitching.app.ui.screen.other

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.theme.Caption1_m
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.SecondaryLightGreen500
import com.kitching.app.ui.theme.ShadowColor
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.dropShadow

//@Preview
@Composable
fun InviteCodeScreen(
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "초대코드",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL
    )
    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_invite),
                    contentDescription = "Invite icon",
                    tint = SecondaryLightGreen500
                )
                Text(
                    modifier = Modifier.padding(bottom = 70.dp),
                    text = "초대코드를 통해 직원을 관리할 수 있어요!",
                    style = Caption1_m.copy(color = NeutralGray800)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .background(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Transparent
                        )
                        .border(
                            width = 1.dp,
                            color = PrimaryGreen300,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp),
                        text = "AJCU3659KHLD",
                        style = H3_m.copy(NeutralGray800)
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp)
                            .size(width = 14.dp, height = 25.dp),
                        painter = painterResource(R.drawable.icon_copy),
                        contentDescription = "copy icon"
                    )
                }
                Row (
                    modifier = Modifier
                        .width(200.dp)
                        .height(49.dp)
                        .dropShadow(
                            shape = RoundedCornerShape(8.dp),
                            offsetX = 0.dp,
                            offsetY = 2.dp,
                            blur = 8.dp,
                            spread = 0.dp,
                            color = ShadowColor
                        )
                        .background(
                            color = NeutralGray0,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.size(29.dp),
                        painter = painterResource(R.drawable.icon_kakao_share),
                        contentDescription = "kakao share icon"
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "카카오톡으로 공유",
                        style = H5_m.copy(NeutralGray800)
                    )
                }
            }
        }
    }
}