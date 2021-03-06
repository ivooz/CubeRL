package model.functor

import model._

/**
 * Created by iozi on 04/11/2015.
 */
class RoomMonad(value : Room) {

  def to(function : Room => Room) = {
    RoomMonad(function(value))
  }

  def mapField(coords : (Int,Int))(fieldFunction : Field => Field) = {
    import value.fields
    RoomMonad(Room(fields.updated(coords._1, fields(coords._1).updated(
      coords._2, fieldFunction(fields(coords._1)(coords._2))))))
  }

  def assert(predicate: Room => Boolean) = predicate(value)

  def getField[A] (coords : (Int,Int)) = FieldMonad(value.fields(coords._1)(coords._2))

  def filterFields(predicate: Field => Boolean) : Vector[Field] = value.fields.flatMap(f => f).filter(predicate)
  
  def getInhabitants : Vector[Creature] = filterFields(_.visitor isDefined).map(_.visitor.get)

  def get = value
}

object RoomMonad {
  def apply(value : Room) = {
    new RoomMonad(value)
  }
}
