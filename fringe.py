import heapq
class Fringe:
    def __init__(self):
        self.heap = []

    def add(self, d, pri):
        heapq.heappush(self.heap, (pri, d))

    def get(self):
        pri, d = heapq.heappop(self.heap)
        return d

    def is_empty(self):
        if self.heap:
            return False
        else:
            return True
