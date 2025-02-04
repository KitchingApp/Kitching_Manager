package com.kitching.app.ui.screen.other.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray100
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import java.time.LocalDate

@Composable
fun NoticeDetailScreen(
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = "공지사항",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { /*TODO*/ }
    )

    val notice = NoticeDTO(
        date = LocalDate.of(2024, 2, 1),
        noticeId = "N001",
        writerId = "MGR001",
        writerName = "민수",
        title = "주방 위생 점검 안내",
        content = "내일 오전 10시에 주방 위생 점검이 진행됩니다. 모든 직원은 위생복을 착용하고, 조리 도구 정리를 철저히 해주시기 바랍니다.\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vulputate semper lacus vitae aliquam. Donec elementum eu turpis eget posuere. Nam felis ipsum, consectetur ut augue nec, ultrices aliquam lacus. Maecenas vitae magna nec dui faucibus malesuada eget posuere dui. Suspendisse vitae sem porta, semper urna ut, gravida metus. Nulla molestie dui ac lacus porttitor facilisis. Aenean eleifend sapien vitae lorem condimentum, sagittis viverra diam feugiat. Donec nec purus felis. Nam quis magna a leo porta rhoncus sed vestibulum justo. Nunc rutrum a nulla et pellentesque.\n" +
                "\n" +
                "Nam accumsan dignissim dui, ac eleifend mi dapibus sit amet. Pellentesque a molestie dui. Phasellus sollicitudin placerat metus vitae varius. Ut at tempus magna. Mauris ornare velit lacinia dui dignissim, eget lobortis sem suscipit. Quisque at volutpat lacus, eu tincidunt eros. Nunc id magna eleifend, commodo turpis ac, finibus augue. Quisque gravida non augue et porttitor. Vestibulum nibh erat, tincidunt vitae malesuada et, pellentesque quis eros. Cras nisl arcu, maximus a eros vitae, dictum vehicula mauris. Quisque volutpat, sapien non dictum tempus, enim elit pharetra nulla, a convallis augue neque gravida lorem. Duis vestibulum luctus justo ut aliquam.\n" +
                "\n" +
                "Nulla facilisi. Integer egestas diam nisi, aliquet rutrum velit dignissim ut. Fusce sed ultrices tellus. Cras lobortis eu purus ultricies aliquam. Nulla malesuada aliquam felis. Integer gravida, nunc vel lacinia efficitur, tellus elit pellentesque arcu, quis tempus felis nisi non lorem. Fusce pulvinar, lacus eu vehicula sagittis, purus nunc gravida mi, non interdum mauris arcu ullamcorper turpis. Cras sollicitudin facilisis enim ut pulvinar. Mauris neque arcu, gravida vitae cursus non, tincidunt sed nulla. Morbi enim est, iaculis ac venenatis et, suscipit quis odio. Nam dui magna, bibendum id ornare et, tristique ac purus. Fusce ac dapibus erat, vitae dictum magna. Quisque cursus nulla ac dictum congue. Curabitur vitae hendrerit magna. Donec imperdiet sollicitudin est quis tempor. Integer quis tempor nulla." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vulputate semper lacus vitae aliquam. Donec elementum eu turpis eget posuere. Nam felis ipsum, consectetur ut augue nec, ultrices aliquam lacus. Maecenas vitae magna nec dui faucibus malesuada eget posuere dui. Suspendisse vitae sem porta, semper urna ut, gravida metus. Nulla molestie dui ac lacus porttitor facilisis. Aenean eleifend sapien vitae lorem condimentum, sagittis viverra diam feugiat. Donec nec purus felis. Nam quis magna a leo porta rhoncus sed vestibulum justo. Nunc rutrum a nulla et pellentesque.\n" +
                "\n" +
                "Nam accumsan dignissim dui, ac eleifend mi dapibus sit amet. Pellentesque a molestie dui. Phasellus sollicitudin placerat metus vitae varius. Ut at tempus magna. Mauris ornare velit lacinia dui dignissim, eget lobortis sem suscipit. Quisque at volutpat lacus, eu tincidunt eros. Nunc id magna eleifend, commodo turpis ac, finibus augue. Quisque gravida non augue et porttitor. Vestibulum nibh erat, tincidunt vitae malesuada et, pellentesque quis eros. Cras nisl arcu, maximus a eros vitae, dictum vehicula mauris. Quisque volutpat, sapien non dictum tempus, enim elit pharetra nulla, a convallis augue neque gravida lorem. Duis vestibulum luctus justo ut aliquam.\n" +
                "\n" +
                "Nulla facilisi. Integer egestas diam nisi, aliquet rutrum velit dignissim ut. Fusce sed ultrices tellus. Cras lobortis eu purus ultricies aliquam. Nulla malesuada aliquam felis. Integer gravida, nunc vel lacinia efficitur, tellus elit pellentesque arcu, quis tempus felis nisi non lorem. Fusce pulvinar, lacus eu vehicula sagittis, purus nunc gravida mi, non interdum mauris arcu ullamcorper turpis. Cras sollicitudin facilisis enim ut pulvinar. Mauris neque arcu, gravida vitae cursus non, tincidunt sed nulla. Morbi enim est, iaculis ac venenatis et, suscipit quis odio. Nam dui magna, bibendum id ornare et, tristique ac purus. Fusce ac dapibus erat, vitae dictum magna. Quisque cursus nulla ac dictum congue. Curabitur vitae hendrerit magna. Donec imperdiet sollicitudin est quis tempor. Integer quis tempor nulla."
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 20.dp),
                            text = notice.title,
                            style = H2.copy(color = NeutralGray800)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            Text(
                                style = Body1_m.copy(color = NeutralGray800),
                                text = notice.writerName
                            )
                            Text(
                                style = H5.copy(color = NeutralGray800),
                                text = "|"
                            )
                            Text(
                                style = Body1_m.copy(color = NeutralGray800),
                                text = "${notice.date}"
                            )
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = notice.content,
                            style = Caption1_R.copy(color = NeutralGray800)
                        )
                        Spacer(Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(19.dp)
                        ) {
                            TextButton(
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(20.dp),
                                onClick = { /*TODO*/ },
                                colors = ButtonColors(
                                    containerColor = PrimaryGreen300,
                                    contentColor = NeutralGray0,
                                    disabledContainerColor = PrimaryGreen300,
                                    disabledContentColor = NeutralGray0
                                )
                            ) {
                                Text(
                                    text = "수정",
                                    style = H5.copy(color = NeutralGray0)
                                )
                            }
                            TextButton(
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(20.dp),
                                onClick = { /*TODO*/ },
                                colors = ButtonColors(
                                    containerColor = NeutralGray100,
                                    contentColor = NeutralGray0,
                                    disabledContainerColor = PrimaryGreen300,
                                    disabledContentColor = NeutralGray0
                                )
                            ) {
                                Text(
                                    text = "삭제",
                                    style = H5_m.copy(color = NeutralGray800)
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}