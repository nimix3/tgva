
package org.telegram.mtproto.transport;

enum ConnectionState {
    TcpConnectionStageSuspended,
    TcpConnectionStageIdle,
    TcpConnectionStageConnecting,
    TcpConnectionStageReconnecting,
    TcpConnectionStageConnected;
    

    private ConnectionState() {
    }
}

