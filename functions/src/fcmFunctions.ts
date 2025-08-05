import { messaging } from 'firebase-admin';
import { onRequest } from 'firebase-functions/v2/https';

// 스케줄 거절 알림 함수 (독립 엔드포인트)
export const sendScheduleRejectMessage = onRequest(async (req, res) => {
  try {
    const { teamName, scheduleDate, scheduleTimeName, rejectReason, registrationToken } = req.body;

    const message = {
      token: registrationToken,
      data: {
        type: 'schedule_reject',
        teamName: teamName,
        scheduleDate: scheduleDate,
        scheduleTimeName: scheduleTimeName,
        rejectReason: rejectReason,
      }
    };

    await messaging().send(message);
    res.status(200).json({ message: 'Schedule reject notification sent successfully' });
  } catch (error) {
    res.status(500).json({ message: `Failed to send notification: ${error}` });
  }
});

// 공지사항 알림 함수 (독립 엔드포인트 - 일괄 전송)
export const sendNoticeMessage = onRequest(async (req, res) => {
  try {
    const { title, writerName, content, fcmTokens } = req.body;

    // 멀티캐스트 메시지 생성
    const multicastMessage = {
      tokens: fcmTokens,
      data: {
        type: 'notice',
        noticeTitle: title,
        writerName: writerName,
        content: content,
      }
    };

    // 일괄 전송
    const response = await messaging().sendEachForMulticast(multicastMessage);

    // 실패한 토큰들의 상세 정보 수집
    const failedTokens: Array<{token: string, error: string}> = [];
    
    if (response.failureCount > 0) {
      response.responses.forEach((result, index) => {
        if (!result.success && result.error) {
          failedTokens.push({
            token: fcmTokens[index],
            error: result.error.message || 'Unknown error'
          });
        }
      });
    }

    // 결과 응답
    res.status(200).json({
      message: 'Notice message processing completed',
      totalTokens: fcmTokens.length,
      successCount: response.successCount,
      failedCount: response.failureCount,
      failedTokens: failedTokens
    });

  } catch (error) {
    res.status(500).json({ message: `Error: ${error}` });
  }
});
