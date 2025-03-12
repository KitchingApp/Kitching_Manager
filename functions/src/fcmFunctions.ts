import { messaging } from 'firebase-admin';
import { onRequest } from 'firebase-functions/v2/https';

export const pushMessage = onRequest(async (req, res) => {
  const { teamName, scheduleDate, scheduleTimeName, rejectReason, registrationToken } = req.body;

  const message = {
    token: registrationToken,
    data: {
      teamName: teamName,
      scheduleDate: scheduleDate,
      scheduleTimeName: scheduleTimeName,
      rejectReason: rejectReason,
    },
  };

  try {
    await messaging().send(message);
    res.status(200).json({ message: 'Notification sent successfully' });
  } catch (error) {
    console.error;
    res.status(500).json({ message: `${error}` });
  }
});
