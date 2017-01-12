
;(function($, window, document, undefined){
	
	class WebSocket{
		
		constructor(name){
			this.stompClient = null;
			this.name = name;
		}
		
		connect(clientUrl,callback) {
			var _this = this;
			
			var socket = new SockJS(_this.name);
			_this.stompClient = Stomp.over(socket);
			_this.stompClient.connect({}, function(frame) {
				console.log('Connected: ' + frame);
				_this.stompClient.subscribe(clientUrl, function(greeting) {
					if(callback){
						callback(greeting);
					}
				});
			});
			
			return _this;
		}
		
		disconnect() {
			if (this.stompClient != null) {
				this.stompClient.disconnect();
			}
			console.log("Disconnected");
		}
		
		sendInfo(serverUrl,data) {
			this.stompClient.send(serverUrl, {}, data);
		}
			
	}

	$.createSocket = function(name){
		return new WebSocket(name);
	}
	
	$.createSocketAndConnect = function(name,clientUrl,callback){
		var socket = new WebSocket(name);
		return socket.connect(clientUrl,callback);
	}
	
})(jQuery, window, document);