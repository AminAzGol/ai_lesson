class Horse:
    x = -1
    y = -1

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __eq__(self, other):
        return other.x == self.x and self.y == other.y

    def __str__(self):
        return str(self.x + 1) + ' ' + str(self.y + 1)

    def __repr__(self):
        return '(' + str(self.x) + ' ' + str(self.y) + ')'

    def __lt__(self, other):
        if self.y < other.y:
            return True
        elif self.y == other.y:
            return self.x < other.x
        return False
