package ru.capjack.tool.csi.transport.server

import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import ru.capjack.tool.utils.Closeable
import ru.capjack.tool.utils.Stoppable
import java.util.concurrent.ScheduledExecutorService

class EventLoopGroups(
	val acceptor: EventLoopGroup,
	val connections: EventLoopGroup
) : Stoppable {
	
	val acceptorAsScheduledExecutorService: ScheduledExecutorService
		get() = connections
	
	val connectionsAsScheduledExecutorService: ScheduledExecutorService
		get() = connections
	
	constructor(
		acceptorThreads: Int = 2,
		connectionsThreads: Int = Runtime.getRuntime().availableProcessors() * 2,
		useEpoll: Boolean = Epoll.isAvailable()
	) : this(
		if (useEpoll) EpollEventLoopGroup(acceptorThreads) else NioEventLoopGroup(acceptorThreads),
		if (useEpoll) EpollEventLoopGroup(connectionsThreads) else NioEventLoopGroup(connectionsThreads)
	)
	
	override fun stop() {
		acceptor.shutdownGracefully().syncUninterruptibly()
		connections.shutdownGracefully().syncUninterruptibly()
	}
}

