import game_info


class Node:
    parent = None
    horses = None
    score = 0
    cost = 0
    dept = 0

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
        return self.cost < other.cost

    def calc_score(self):
        width, height = game_info.get_width_height()
        m = 0
        for h in self.horses:
            m += h.x + h.y * height
        self.score = m
