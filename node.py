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
        for h in self.horses:
            if h not in other.horses:
                return False
        return True

    def __hash__(self):
        return hash(self.horses)

    def __lt__(self, other):
        return self.priority < other.priority
