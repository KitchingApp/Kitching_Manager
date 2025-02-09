package com.kitching.app.ui.screen.other.memberlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray200
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.ShadowColor
import com.kitching.app.util.dropShadow
import com.kitching.domain.entities.StaffLevel

/**
 * Staff level dropdown component
 *
 * @param staffLevels 직급 리스트
 * @param selectedStaffLevel 선택된 직급 관리
 * @param isExpanded 드롭다운 펼쳐짐 여부
 */
@Composable
fun StaffLevelDropdownComponent(
    staffLevels: List<StaffLevel>,
    selectedStaffLevel: MutableState<StaffLevel>,
    isExpanded: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .dropShadow(
                color = ShadowColor,
                offsetY = 2.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .wrapContentSize()
            .background(color = NeutralGray0, shape = RoundedCornerShape(8.dp))
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
                .width(320.dp)
                .height(56.dp)
                .padding(20.dp, 10.dp)
                .clickable { isExpanded.value = !isExpanded.value },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = if (selectedStaffLevel.value.staffLevelId.isNotEmpty())
                    selectedStaffLevel.value.staffLevelName
                else "직급을 선택해주세요",
                style = Body1_m.copy(color = NeutralGray800)
            )
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                onClick = { isExpanded.value = !isExpanded.value }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(
                        if (isExpanded.value) R.drawable.icon_up_triangle
                        else R.drawable.icon_down_triangle
                    ),
                    contentDescription = "arrow",
                    tint = NeutralGray800
                )
            }
        }

        AnimatedVisibility(visible = isExpanded.value) {
            Card(
                modifier = Modifier.width(240.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardColors(
                    containerColor = NeutralGray0,
                    contentColor = NeutralGray800,
                    disabledContainerColor = NeutralGray0,
                    disabledContentColor = NeutralGray800
                )
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp)
                ) {
                    items(staffLevels) { staffLevel ->
                        DropDownStaffLevelItem(
                            staffLevel = staffLevel,
                            onClickItem = {
                                selectedStaffLevel.value = staffLevel
                                isExpanded.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Drop down staff level item
 *
 * @param staffLevel
 * @param onClickItem
 * @receiver
 */
@Composable
fun DropDownStaffLevelItem(
    staffLevel: StaffLevel,
    onClickItem: (StaffLevel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(staffLevel) }
            .padding(20.dp, 10.dp)
    ) {
        Text(
            text = staffLevel.staffLevelName,
            style = Body1_m.copy(NeutralGray800),
            textAlign = TextAlign.Start
        )
    }
}
