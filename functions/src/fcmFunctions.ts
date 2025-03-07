import { messaging } from 'firebase-admin';
import { onRequest } from 'firebase-functions/v2/https';

export const pushMessage = onRequest(async (req, res) => {
  const { teamName, scheduleDate, scheduleTimeName, rejectReason, registrationToken } = req.body;

  const titleTemplete = `${teamName}의 ${scheduleDate} ${scheduleTimeName} 스케줄 신청이 반려되었어요.`;
  const bodyTemplete = `반려 사유: ${rejectReason}`;

  const message = {
    notification: {
      title: titleTemplete,
      body: bodyTemplete,
    },
    token: registrationToken,
  };

  try {
    await messaging().send(message);
    res.status(200).json({ message: 'Notification sent successfully' });
  } catch (error) {
    console.error;
    res.status(500).json({ message: 'Error sending notification' });
  }
});
