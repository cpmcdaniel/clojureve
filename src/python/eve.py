import zlib
import zmq

import simplejson

def main():
    context = zmq.Context()
    subscriber = context.socket(zmq.SUB)

    subscriber.connect("tcp://relay-us-east-1.eve-emdr.com:8050")
    subscriber.setsockopt_string(zmq.SUBSCRIBE, "")
    
    while True:
        market_json = zlib.decompress(subscriber.recv())
        market_data = simplejson.loads(market_json)
        print(market_data)


if __name__ == '__main__':
    main()
