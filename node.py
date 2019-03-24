import multiprocessing
class Node:
    parent = None
    horses = None
    priority = 1

    def __init__(self, parent, horses):
        self.parent = parent
        self.horses = horses

    def __repr__(self):
        return str(self.horses)

    def __eq__(self, other):
        """the lists must be sorted"""
        z = zip(self.horses, other.horses)
        for i, j in z:
            if i != j:
                return False
        return True

    def __hash__(self):
        return hash(self.horses)

    def __lt__(self, other):
        return self.priority < other.priority
