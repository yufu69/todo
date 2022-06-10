(ns todo.boundary.todos
  (:require [clojure.java.jdbc :as jdbc]))

(defprotocol Todos
  (get-todos [db])
  (find-todo [db id])
  (create-todo [db params]))

(extend-protocol Todos
  duct.database.sql.Boundary

  (get-todos [{:keys [spec]}]
    (jdbc/query spec ["SELECT * FROM todos"]))

  (find-todo [{:keys [spec]} id]
    (jdbc/query spec ["SELECT * FROM todos WHERE id = ?" id]))

  ;; "No such var: jdbc/get-by-id" ってコンパイルエラーが出る
  ;; (find-todo [{:keys [spec]} id]
  ;;   (jdbc/get-by-id spec :todos id))

  (create-todo [{:keys [spec]} params]
    (jdbc/insert! spec :todos {:title (:title params)})))