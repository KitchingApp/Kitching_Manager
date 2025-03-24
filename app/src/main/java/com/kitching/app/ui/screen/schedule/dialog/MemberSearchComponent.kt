package com.kitching.app.ui.screen.schedule.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray200
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.domain.entities.Member

@Composable
fun MemberSearchComponent(
    members: List<Member>,
    selectedMember: Member,
    onMemberSelected: (member: Member) -> Unit
) {

    /** 사용자가 입력한 텍스트를 저장 */
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    /** 드롭다운 펼쳐짐 상태 */
    var isExpanded by remember { mutableStateOf(false) }

    /** 드롭다운 열림 상태가 변경될때마다 실행 */
    LaunchedEffect(isExpanded) {
        if (!isExpanded) {
            textState = TextFieldValue(
                text = selectedMember.userName,
                selection = TextRange(selectedMember.userName.length)
            )
        }
    }

    Column(
        modifier = Modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = NeutralGray800,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .width(240.dp)
                .height(34.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp)
            ) {
                // 텍스트가 비어 있을 때만 placeholder를 표시
                if (textState.text.isEmpty()) {
                    Text(
                        text = "검색할 멤버를 입력하세요",
                        style = H5_m.copy(color = NeutralGray300),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textState,
                    onValueChange = {
                        textState = it
                        isExpanded = true
                    },
                    textStyle = H3_m.copy(color = NeutralGray800),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    cursorBrush = SolidColor(NeutralGray800),
                )
            }
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                onClick = { isExpanded = !isExpanded }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(
                        if (isExpanded) R.drawable.icon_up_triangle
                        else R.drawable.icon_down_triangle
                    ),
                    contentDescription = "arrow",
                    tint = NeutralGray800
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Card(
                modifier = Modifier
                    .width(240.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardColors(
                    containerColor = NeutralGray200,
                    contentColor = NeutralGray800,
                    disabledContainerColor = NeutralGray200,
                    disabledContentColor = NeutralGray0
                )
            ) {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp)
                ) {
                    members
                        .filter { it.userName.contains(textState.text.lowercase()) || textState.text.isEmpty() }
                        .sortedBy { it.userName }.forEach { member ->
                            item(key = member.userId) {
                                DropDownMemberList(
                                    member = member,
                                ) { memberItem ->
                                    textState = TextFieldValue(
                                        text = memberItem.userName,
                                        selection = TextRange(memberItem.userName.length)
                                    )
                                    onMemberSelected(memberItem)
                                    isExpanded = false
                                }
                            }
                        }
                }
            }
        }
    }
}

/** 드롭다운 아이템 하나를 정의 */
@Composable
fun DropDownMemberList(
    member: Member,
    onClickItem: (Member) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickItem(member)
            }
            .padding(20.dp, 10.dp)
    ) {
        Text(
            text = member.userName,
        )
    }
}