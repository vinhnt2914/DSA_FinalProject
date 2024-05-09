import { Card, CardBody } from '@nextui-org/react'
import React from 'react'
import { BsExclamationCircle } from 'react-icons/bs'
import { CiCircleInfo } from 'react-icons/ci'
import { RxCrossCircled } from 'react-icons/rx'
import { SiTicktick } from 'react-icons/si'

export default function NotificationDiag({
  notifState: typeOfNotification,
  content,
}) {
  // Notification color
  const notifStateColor = {
    success: 'bg-green-400',
    error: 'bg-red-400',
    warning: 'bg-yellow-400',
    info: 'bg-blue-400',
  }
  // Notification header
  const notificationHeader = {
    success: <SiTicktick />,
    error: <RxCrossCircled /> + ' Error!',
    warning: <BsExclamationCircle />,
    info: <CiCircleInfo />,
  }
  // Header in text
  const notificationHeaderText = {
    success: 'Success!',
    error: 'Error!',
    warning: 'Warning!',
    info: 'Information!',
  }
  return (
    <div className="z-[1000] fixed bottom-24 right-4">
      <Card className="shadow-lg ">
        <CardBody className={notifStateColor[typeOfNotification]}>
          <div className="flex justify-start items-center">
            {notificationHeader[typeOfNotification]}
            <h1 className="pl-2 font-bold">
              {notificationHeaderText[typeOfNotification]}
            </h1>
          </div>
          <p>{content}</p>
        </CardBody>
      </Card>
    </div>
  )
}
