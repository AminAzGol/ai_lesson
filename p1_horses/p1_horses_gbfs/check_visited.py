
def add_to_visited(node, visited):
    if not visited:
        visited.insert(0, node)
        return
    if visited[len(visited) - 1].score < node.score:
        visited.insert(len(visited) - 1, node)
    elif visited[0].score > node.score:
        visited.insert(0, node)
    else:
        add_to_visited_rec(node, visited, 0, len(visited) - 1)


def add_to_visited_rec(node, visited, first, last):
    if visited[last].score < node.score < visited[first].score:
        return False
    if last - first == 1 or 0 == last - first:
        visited.insert(last, node)
        return 1
    mid = int((first + last) / 2)
    if visited[mid].score == node.score:
        visited.insert(mid, node)
    else:
        add_to_visited_rec(node, visited, mid, last)
        add_to_visited_rec(node, visited, first, mid)


def check_visited(node, visited):
    for n in visited:
        if node.score < n.score:
            return False
        elif node == n:
            return True
    return False
    # check_repeat(visited)
    # if not visited:
    #     return False
    # else:
    #     return check_visited_rec(node, visited, 0, len(visited) - 1)


def check_visited_rec(node, visited, first, last):
    if visited[last].score < node.score < visited[first].score:
        return False
    if last - first == 1 or 0 == last - first:
        a = visited[first] == node or visited[last] == node
        if a:
            return True
        else:
            return False
    mid = int((first + last) / 2)
    if visited[mid] == node:
        return True
    else:
        ret1 = check_visited_rec(node, visited, mid, last)
        ret2 = check_visited_rec(node, visited, first, mid)
        return ret1 or ret2


def check_repeat(l):
    for i in l:
        for j in l:
            a = 0
            if i == j:
                a += 1
            if a == 2:
                return True
    return False