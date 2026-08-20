import { useEffect, useRef } from 'react'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'

const WS_URL = import.meta.env.VITE_WS_URL || 'http://localhost:8080/ws'

function useWebSocket(userId, onMessage) {
  const clientRef = useRef(null)

  useEffect(() => {
    if (!userId) return

    const socket = new SockJS(WS_URL)
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