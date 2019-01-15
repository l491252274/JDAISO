package {
	import flash.display.Sprite;
	import flash.events.*;
	import flash.external.ExternalInterface;
	import flash.filters.GlowFilter;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFieldType;
	import flash.utils.Timer;
	import flash.net.XMLSocket;

	public class PushData extends Sprite {
		private var input:TextField;
		private var output:TextField;
		private var sendBtn:Sprite;

		private var hostName:String = "127.0.0.1";
		private var port:uint = 5555;
		private var socket:XMLSocket;

		public function PushData() {

			socket = new XMLSocket();
			configureListeners(socket);
			socket.connect(hostName, port);
            
			//-----------------初始化UI------------start--------------
			input = new TextField();
			input.type = TextFieldType.INPUT;
			input.background = true;
			input.border = true;
			input.borderColor = 0x7f9db9;
			input.width = 274;
			input.height = 20;
			input.x = 8;
			input.y = 16;
			addChild(input);

			sendBtn = new Sprite();
			sendBtn.mouseEnabled = true;
			sendBtn.x = input.width + 8 + 6;
			sendBtn.y = 16;
			sendBtn.graphics.lineStyle(1, 0x7f9db9, 1);
			sendBtn.graphics.beginFill(0xCCCCCC);
			sendBtn.graphics.drawRoundRect(0, 0, 140, 20, 0, 0);
			sendBtn.graphics.endFill();
			//添加发送事件
			sendBtn.addEventListener(MouseEvent.CLICK, clickHandler);
			addChild(sendBtn);

			var label : TextField = new TextField();
			label.text = "发送数据到JS";
			label.autoSize = TextFieldAutoSize.LEFT;
			label.x = sendBtn.x+ Math.round((sendBtn.width - label.width)/2);
			label.y = sendBtn.y;
			label.selectable = false;
			label.mouseEnabled = false;
			addChild(label);

			this.graphics.lineStyle(1, 0x7f9db9, 1);
			this.graphics.moveTo(8, 40);
			this.graphics.lineTo(428, 40);
			this.graphics.lineTo(428, 203);
			this.graphics.lineTo(8, 203);
			this.graphics.lineTo(8, 40);

			output = new TextField();
			output.y = 40;
			output.x = 10;
			output.width = 420;
			output.height = 160;
			output.multiline = true;
			output.wordWrap = true;
			output.textColor = 0x00ff00;
			output.filters = [new GlowFilter(0x000000, 0.8, 2, 2, 8, 3)];
			output.text = "初始化...\n";
			addChild(output);
			//-----------------初始化UI---------end-----------------


			//指示此播放器是否位于提供外部接口的容器中。 如果外部接口可用，则此属性为 true；否则，为 false。
			if (ExternalInterface.available) {//point 1[/b]
				try {
					//对JavaScript开放一个方法;
					ExternalInterface.addCallback("sendToActionScript", receivedFromJavaScript);
					if (checkJavaScriptReady()) {
						output.appendText("JavaScript is ready.\n");
					} else {
						//如果JavaScript is not ready，新建一定时器，没0.1秒检测一次,直至JavaScript is ready;
						output.appendText("JavaScript is NOT ready, 0.1秒后重试.\n");
						var readyTimer:Timer = new Timer(100, 0);
						readyTimer.addEventListener(TimerEvent.TIMER, timerHandler);
						readyTimer.start();
					}
				} catch (error:SecurityError) {
					output.appendText("A SecurityError occurred(安全错误): " + error.message + "\n");
				} catch (error:Error) {
					output.appendText("An Error occurred（错误）: " + error.message + "\n");
				}
			} else {
				output.appendText("External interface不可用。");
			}
		}
		/**
		        * 对JavaScript开放的名为sendToActionScript的方法。
		        * 在JavaScript中这样调用：.sendToActionScript(instring),
		        * instring就传给本方法的value了。
		        */
		private function receivedFromJavaScript(value:String):void {
			socket.connect(hostName, port);
			socket.send(value+"\n");
			//output.appendText("JavaScript 说: " + value + "\n");
		}
		/**
		        * 调用JavaScript暴露的方法 isReady
		        */
		private function checkJavaScriptReady():Boolean {
			var isReady:Boolean = ExternalInterface.call("isReady");
			return isReady;
		}
		private function timerHandler(event:TimerEvent):void {
			output.appendText("检查 JavaScript 状态...\n");
			if (checkJavaScriptReady()) {
				output.appendText("JavaScript is ready.\n");
				Timer(event.target).stop();
			}
		}
		/**
		        * 鼠标点击发送时， 调用JavaScript暴露的方法 sendToJavaScript
		        */
		private function clickHandler(event:MouseEvent):void {
			socket.connect(hostName, port);
			socket.send(input.text+"\n");
			if (ExternalInterface.available) {
				ExternalInterface.call("sendToJavaScript", input.text);
			}
		}
		//-----------------XMLSocket------------start--------------
		private function configureListeners(dispatcher:IEventDispatcher):void {
			dispatcher.addEventListener(Event.CLOSE, closeHandler);
			dispatcher.addEventListener(Event.CONNECT, connectHandler);
			dispatcher.addEventListener(DataEvent.DATA, dataHandler);
			dispatcher.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
			dispatcher.addEventListener(ProgressEvent.PROGRESS, progressHandler);
			dispatcher.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
		}
		private function connectHandler(event:Event):void {
			
			trace("connectHandler链接成功");
			output.appendText("connectHandler链接成功\n");
		}

		private function dataHandler(event:DataEvent):void {
			///socket.send("<policy-file-xxquest/>\n");
			trace("收到数据： "+event.text);
			//output.appendText("收到数据: "+event.text+"\n");
			trace("dataHandler: " + event);
			//output.appendText("dataHandler: " + event+"\n");
			if (ExternalInterface.available) {
				ExternalInterface.call("sendToJavaScript", event.text);
			}
		}

		private function ioErrorHandler(event:IOErrorEvent):void {
			trace("ioErrorHandler: " + event);
			output.appendText("ioErrorHandler: " + event+"\n");
		}

		private function progressHandler(event:ProgressEvent):void {
			trace("progressHandler链接成功");
			output.appendText("progressHandler链接成功"+"\n");
			trace("progressHandler loaded:" + event.bytesLoaded + " total: " + event.bytesTotal);
			output.appendText("progressHandler loaded:" + event.bytesLoaded + " total: " + event.bytesTotal+"\n");
		}

		private function closeHandler(event:Event):void {
			trace("closeHandler: " + event);
			output.appendText("closeHandler: " + event+"\n");
		}

		private function securityErrorHandler(event:SecurityErrorEvent):void {
			trace("securityErrorHandler: " + event);
			output.appendText("securityErrorHandler: "  + event+"\n");
		}
		//-----------------XMLSocket------------end--------------

	}
}