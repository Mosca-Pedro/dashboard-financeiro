import { useEffect, useRef } from 'react'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'

function useWebSocket(userId, onMessage) {
  const clientRef = useRef(null)

  useEffect(() => {
    if (!userId) return

    const socket = new SockJS('http://localhost:8080/ws')
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe(`/topic/users/${userId}/transactions`, (message) => {
          const data = JSON.parse(message.body)
          onMessage(data)
        })
      },
    })

    client.activate()
    clientRef.current = client

    return () => {
      client.deactivate()
    }
  }, [userId, onMessage])
}

export default useWebSocket